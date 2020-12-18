package com.github.spring.expand.util;

import com.github.spring.expand.test.TestEntity;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Bean 工具类
 *
 * @author wx
 * @date 2020/12/16 11:40
 */
public final class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /**
     * 属性拷贝
     *
     * @param source
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, T obj) {
        if (obj == null) return null;
        if (source == null) return obj;
        BeanUtils.copyProperties(source, obj);
        return obj;
    }

    /**
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Map<String, Object> source, Class<T> clazz) {
        T res = null;
        try {
            res = clazz.newInstance();
            if (source != null) {
                org.apache.commons.beanutils.BeanUtils.populate(res, source);
            }
        } catch (InstantiationException e) {
            logger.error("copyProperties Error!", e);
        } catch (IllegalAccessException e) {
            logger.error("copyProperties Error!", e);
        } catch (InvocationTargetException e) {
            logger.error("copyProperties Error!", e);
        }
        return res;
    }

    /**
     * 属性拷贝
     *
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copyProperties(Object source, Class<T> clazz) {
        T res = null;
        try {
            res = clazz.newInstance();
            if (source != null) {
                BeanUtils.copyProperties(source, res);
            }
        } catch (InstantiationException e) {
            logger.error("copyProperties Error!", e);
        } catch (IllegalAccessException e) {
            logger.error("copyProperties Error!", e);
        }
        return res;
    }

    /**
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> copyProperties(List<Object> source, Class<T> clazz) {
        if (CollectionUtils.isNotEmpty(source)) {
            return source.stream().map(obj -> copyProperties(obj, clazz)).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

}
