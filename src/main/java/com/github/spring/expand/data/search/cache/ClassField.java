package com.github.spring.expand.data.search.cache;

import com.google.common.base.CaseFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author wx
 * @date 2020/12/7 16:26
 */
public class ClassField implements java.io.Serializable {

    private Logger log = LoggerFactory.getLogger(ClassField.class);

    private final Class<?> clazz;
    // 类名称
    private String entityName;
    // 表名称
    private String tableName;
    //
    private List<ClassFieldInfo> classFieldInfo = new ArrayList<>();

    private List<Class<?>> transitionClassList = new ArrayList<>(); //已经转换过的类 避免重复解析

    public ClassField(Class<?> clazz) {
        //
        this.clazz = clazz;
        this.init();
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ClassFieldInfo> getClassFieldInfo() {
        return classFieldInfo;
    }

    public void setClassFieldInfo(List<ClassFieldInfo> classFieldInfo) {
        this.classFieldInfo = classFieldInfo;
    }


    /**
     * 获取查询条件
     * 待支持. 分割符号
     *
     * @param key
     * @return
     */
    public ClassFieldInfo getFieldInfo(String key) {
        ClassFieldInfo fieldInfo = null;
        for (ClassFieldInfo field : classFieldInfo) {
            String fieldName = field.getFieldName();
            String dbFieldName = field.getDbFieldName();
            if (StringUtils.equalsIgnoreCase(key, fieldName) || StringUtils.equalsIgnoreCase(key, dbFieldName)) {
                fieldInfo = field;
                break;
            }
        }
        return fieldInfo;
    }

    /**
     * 初始化
     */
    private void init() {
        String entityName = this.clazz.getSimpleName();
        Table table = this.clazz.getAnnotation(Table.class);
        String tableName = "";
        String schema = "";
        if (table != null) {
            tableName = table.name();
            schema = table.schema();
        }
        if (StringUtils.isBlank(tableName)) {
            tableName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityName);
        }
        if (StringUtils.isNotBlank(schema)) {
            tableName = schema + "." + tableName;
        }
        this.tableName = tableName;
        this.entityName = entityName;
        this.classFieldInfo.addAll(getClassFieldInfo(this.clazz));
    }

    /**
     * @param clazz
     * @return
     */
    private List<ClassFieldInfo> getClassFieldInfo(Class<?> clazz) {
        Field[] fields = getAllFields(clazz);
        List<ClassFieldInfo> res = new ArrayList<>();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            String name = field.getName();
            PropertyDescriptor descriptor = null;
            try {
                descriptor = new PropertyDescriptor(name, clazz);
            } catch (IntrospectionException e) {
                // TODO 没有get/set方法
                log.error("类[{}],字段[{}]没有设置get方法", clazz, name);
            }
            if (descriptor == null) {
                continue;
            }
            Transient t = getAnnotation(field, descriptor, Transient.class);
            if (t != null) {
                // 忽略@Transient
                continue;
            }
            Column column = getAnnotation(field, descriptor, Column.class);
            String dn = "";
            if (column != null) {
                dn = column.name();
            }
            if (StringUtils.isBlank(dn)) {
                dn = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
            }

            ClassFieldInfo classFieldInfo = new ClassFieldInfo(name, dn, field.getType(), field.getType().getSimpleName());

            // 关联查询支持
            OneToOne oneToOne = getAnnotation(field, descriptor, OneToOne.class);
            boolean association = false;
            Class<?> associationClazz = null;
            if (oneToOne != null) {
                association = true;
                associationClazz = field.getType();
                classFieldInfo.setOneToOne(true);
            }
            OneToMany oneToMany = getAnnotation(field, descriptor, OneToMany.class);
            if (oneToMany != null) {
                // 集合
                associationClazz = getCollectionClass(field.getType(), field);
                association = true;
                classFieldInfo.setOneToMany(true);
            }
            ManyToOne manyToOne = getAnnotation(field, descriptor, ManyToOne.class);
            if (manyToOne != null) {
                associationClazz = field.getType();
                association = true;
                classFieldInfo.setManyToOne(true);
            }
            ManyToMany manyToMany = getAnnotation(field, descriptor, ManyToMany.class);
            if (manyToMany != null) {
                associationClazz = getCollectionClass(field.getType(), field);
                association = true;
                classFieldInfo.setManyToMany(true);
            }
            boolean transition = true;
            // 对象必须为Entity的才会记录
            if (association && associationClazz != null && com.github.spring.expand.base.Entity.class.isAssignableFrom(associationClazz)) {
                if (!transitionClassList.contains(associationClazz)) {
                    transitionClassList.add(associationClazz);
                    List<ClassFieldInfo> il = getClassFieldInfo(associationClazz);
                    classFieldInfo.setAssociationProperty(il);
                }
            } else {
                transition = false;
            }
            classFieldInfo.setTransition(transition);
            classFieldInfo.setAssociation(association);
            res.add(classFieldInfo);
        }
        return res;
    }

    /**
     * 获取泛型的类型
     *
     * @param clazz
     * @param field
     * @return
     */
    private Class<?> getCollectionClass(Class<?> clazz, Field field) {
        if (Collection.class.isAssignableFrom(clazz)) {
            Type genericType = field.getGenericType(); // 当前集合的泛型类型
            if (null == genericType) {
                return null;
            }
            if (genericType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) genericType;
                // 得到泛型里的class类型对象
                Class<?> actualTypeArgument = (Class<?>) pt.getActualTypeArguments()[0];
                return actualTypeArgument;
            }
        }
        return null;
    }

    /**
     * 获取注解
     *
     * @param field
     * @param descriptor
     * @param clazz
     * @param <T>
     * @return
     */
    private <T extends Annotation> T getAnnotation(Field field, PropertyDescriptor descriptor, Class<T> clazz) {
        T res = field.getAnnotation(clazz);
        if (res == null) {
            Method readMethod = descriptor.getReadMethod();
            if (readMethod != null) {
                res = readMethod.getAnnotation(clazz);
            }
        }
        return res;
    }

    /**
     * 获取所有的列
     *
     * @param clazz
     * @return
     */
    private Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        //  MappedSuperclass
        while (clazz != null && (clazz.isAnnotationPresent(Entity.class) || clazz.isAnnotationPresent(MappedSuperclass.class))) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
