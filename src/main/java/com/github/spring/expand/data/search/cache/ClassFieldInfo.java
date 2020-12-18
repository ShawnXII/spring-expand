package com.github.spring.expand.data.search.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wx
 * @date 2020/12/7 16:39
 */
public class ClassFieldInfo implements Serializable {
    private String fieldName; // entity字段

    private String dbFieldName; // db字段

    private Class<?> valueType; // 字段类型

    private String typeName; // 类型名称

    // 一对一
    private Boolean oneToOne = false;

    private Boolean oneToMany = false;

    private Boolean ManyToOne = false;

    private Boolean ManyToMany = false;

    private Boolean association = false; //是否是关联属性

    private boolean transition = true; //关联属性是否转换成功
    private List<ClassFieldInfo> associationProperty = new ArrayList<>(); // 关联属性

    private Boolean isBasicType = false; // 是否是基本类型

    public ClassFieldInfo(String fieldName, String dbFieldName, Class<?> valueType, String typeName) {
        this.fieldName = fieldName;
        this.dbFieldName = dbFieldName;
        this.valueType = valueType;
        this.typeName = typeName;
    }

    public ClassFieldInfo() {
        super();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getDbFieldName() {
        return dbFieldName;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public void setValueType(Class<?> valueType) {
        this.valueType = valueType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean getOneToOne() {
        return oneToOne;
    }

    public void setOneToOne(Boolean oneToOne) {
        this.oneToOne = oneToOne;
    }

    public Boolean getOneToMany() {
        return oneToMany;
    }

    public void setOneToMany(Boolean oneToMany) {
        this.oneToMany = oneToMany;
    }

    public Boolean getManyToOne() {
        return ManyToOne;
    }

    public void setManyToOne(Boolean manyToOne) {
        ManyToOne = manyToOne;
    }

    public Boolean getManyToMany() {
        return ManyToMany;
    }

    public void setManyToMany(Boolean manyToMany) {
        ManyToMany = manyToMany;
    }

    public Boolean getAssociation() {
        return association;
    }

    public void setAssociation(Boolean association) {
        this.association = association;
    }

    public boolean isTransition() {
        return transition;
    }

    public void setTransition(boolean transition) {
        this.transition = transition;
    }

    public List<ClassFieldInfo> getAssociationProperty() {
        return associationProperty;
    }

    public void setAssociationProperty(List<ClassFieldInfo> associationProperty) {
        this.associationProperty = associationProperty;
    }

    public Boolean getBasicType() {
        return isBasicType;
    }

    public void setBasicType(Boolean basicType) {
        isBasicType = basicType;
    }
}
