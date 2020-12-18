package com.github.spring.expand.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * JSON工具类
 *
 * @author wx
 * @date 2020/12/16 11:40
 */
public final class JsonUtil {
    /**
     * Object_Mapper 对象
     */
    public final static ObjectMapper MAPPER = new ObjectMapper();


    private final static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    static {
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true); //设置可用单引号
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//设置字段可以不用双引号包括
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));//设置时间格式
    }

    /**
     * 返回Json字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("对象转JSON字符串失败!", e);
        }
        return null;
    }

    /**
     * Json 转换为对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.error("JSON字符串转对象失败!", e);
        }
        return null;
    }

    /**
     * Json 转List对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return MAPPER.readValue(json, javaType);
        } catch (JsonProcessingException e) {
            logger.error("JSON字符串转对象失败!", e);
        }
        return null;
    }
}
