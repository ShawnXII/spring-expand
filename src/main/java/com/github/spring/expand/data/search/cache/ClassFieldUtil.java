package com.github.spring.expand.data.search.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wx
 * @date 2020/12/7 16:41
 */
public final class ClassFieldUtil {
    /**
     * ClassField 缓存 使用ConcurrentHashMap 保证线程安全
     */
    private static final Map<Class, ClassField> CLASS_FIELD = new ConcurrentHashMap<>();

    /**
     * 获取class 缓存管理 避免大量的重复加载
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T extends Object> ClassField getClassField(Class<T> clazz) {
        if (!CLASS_FIELD.containsKey(clazz)) {
            synchronized (ClassFieldUtil.class) {
                if (!CLASS_FIELD.containsKey(clazz)) {
                    ClassField classField = new ClassField(clazz);
                    CLASS_FIELD.put(clazz, classField);
                    return classField;
                }
            }
        }
        return CLASS_FIELD.get(clazz);
    }
}
