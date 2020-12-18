package com.github.spring.expand.data.search;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.spring.expand.data.search.build.BuildFactory;
import com.github.spring.expand.data.search.cache.ClassField;
import com.github.spring.expand.data.search.cache.ClassFieldUtil;
import com.github.spring.expand.data.search.condition.Condition;
import com.github.spring.expand.data.search.operator.EnumOperator;
import com.github.spring.expand.util.JsonUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 查询工具类
 *
 * @author wx
 * @date 2020/12/4 16:56
 */
public final class Searchable {

    private static final String[] DEFAULT_FORMATS = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};
    /**
     * 类
     */
    private final Class<?> clazz;
    /**
     *
     */
    private final ClassField classField;

    /**
     *
     */
    private SearchableConfig config;

    /**
     * @param clazz
     */
    private Searchable(Class<?> clazz) {
        this.clazz = clazz;
        this.classField = ClassFieldUtil.getClassField(this.clazz);
        this.config = new SearchableConfig(this.classField);
    }

    /**
     * 创建一个查询条件类
     *
     * @param clazz
     * @return
     */
    public static Searchable of(Class<?> clazz) {
        return new Searchable(clazz);
    }

    /**
     * 占位查询条件
     *
     * @param key
     * @param operator
     * @return
     */
    private Searchable wherePlaceholder(String key, EnumOperator operator) {
        Condition condition = new Condition(key, operator, classField);
        condition.setValid(false);
        this.config.add(condition);
        return this;
    }

    /**
     * @param key
     * @param operator
     * @return
     */
    public Searchable where(String key, EnumOperator operator) {
        Condition condition = new Condition(key, operator, classField);
        this.config.add(condition);
        return this;
    }

    /**
     * List 对象
     *
     * @param key
     * @param operator
     * @param value
     * @return
     */
    public Searchable where(String key, EnumOperator operator, List<Object> value) {
        Condition condition = new Condition(key, operator, classField);
        condition.setListValue(value);
        this.config.add(condition);
        return this;
    }

    /**
     * where 条件
     *
     * @param key
     * @param operator
     * @param value
     * @return
     */
    public Searchable where(String key, EnumOperator operator, Object value) {
        Condition condition = new Condition(key, operator, classField);
        condition.setObjValue(value);
        this.config.add(condition);
        return this;
    }

    /**
     * @param key
     * @param operator
     * @param value
     * @return
     */
    public Searchable where(String key, EnumOperator operator, Date[] value) {
        Condition condition = new Condition(key, operator, classField);
        condition.setBetweenDate(value);
        this.config.add(condition);
        return this;
    }

    /**
     * @param key
     * @param operator
     * @param value
     * @return
     */
    public Searchable where(String key, EnumOperator operator, Number[] value) {
        Condition condition = new Condition(key, operator, classField);
        condition.setBetweenNumber(value);
        this.config.add(condition);
        return this;
    }

    /**
     * where 条件
     *
     * @param key
     * @param operator
     * @param value
     * @return
     */
    public Searchable where(String key, EnumOperator operator, Number value) {
        Condition condition = new Condition(key, operator, classField);
        condition.setNumberValue(value);
        this.config.add(condition);
        return this;
    }


    /**
     * where 条件
     *
     * @param key
     * @param operator
     * @param value
     * @return
     */
    public Searchable where(String key, EnumOperator operator, CharSequence value) {
        Condition condition = new Condition(key, operator, classField);
        condition.setStrValue(value);
        this.config.add(condition);
        return this;
    }

    /**
     * where 条件
     *
     * @param key
     * @param operator
     * @param value
     * @return
     */
    public Searchable where(String key, EnumOperator operator, Date value) {
        Condition condition = new Condition(key, operator, classField);
        condition.setDateValue(value);
        this.config.add(condition);
        return this;
    }

    /**
     * And 查询
     *
     * @return
     */
    public Searchable and() {
        this.config.and();
        return this;
    }

    /**
     * OR 查询
     *
     * @return
     */
    public Searchable or() {
        this.config.or();
        return this;
    }

    /**
     * 等于
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable eq(String key, Number value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.EQ);
        }
        return where(key, EnumOperator.EQ, value);
    }

    /**
     * 等于 字符串类型
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable eq(String key, CharSequence value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.EQ);
        }
        return where(key, EnumOperator.EQ, value);
    }

    /**
     * 等于
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable eq(String key, Date value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.EQ);
        }
        return where(key, EnumOperator.EQ, value);
    }

    /**
     * 等于会统一转换为字符串
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable eq(String key, Object value) {
        if (value != null) {
            return eq(key, value.toString());
        }
        return wherePlaceholder(key, EnumOperator.EQ);
    }

    /**
     * 不等于
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable ne(String key, Object value) {
        if (value != null) {
            return ne(key, value.toString());
        }
        return wherePlaceholder(key, EnumOperator.NE);
    }

    /**
     * 不等于 Number类型
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable ne(String key, Number value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.NE);
        }
        return where(key, EnumOperator.NE, value);
    }

    /**
     * 不等于 字符串类型
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable ne(String key, CharSequence value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.NE);
        }
        return where(key, EnumOperator.NE, value);
    }

    /**
     * 不等于 时间类型
     *
     * @param key
     * @param value null值会直接被忽略掉
     * @return
     */
    public Searchable ne(String key, Date value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.NE);
        }
        return where(key, EnumOperator.NE, value);
    }

    /**
     * 小于
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable lt(String key, Number value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.LT);
        }
        return where(key, EnumOperator.LT, value);
    }

    /**
     * 小于
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable lt(String key, Date value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.LT);
        }
        return where(key, EnumOperator.LT, value);
    }

    /**
     * 小于 会自动转换为时间格式
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable lt(String key, String value) {
        return lt(key, value, DEFAULT_FORMATS);
    }

    /**
     * 小于 转换为时间格式
     *
     * @param key
     * @param value
     * @param format
     * @return
     */
    public Searchable lt(String key, CharSequence value, String... format) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.LT);
        }
        Date date = SearchUtil.toDate(value.toString(), format);
        if (date == null) {
            return wherePlaceholder(key, EnumOperator.LT);
        }
        return lt(key, date);
    }

    /**
     * 小于等于 转换为时间格式
     *
     * @param key
     * @param value
     * @param format
     * @return
     */
    public Searchable le(String key, CharSequence value, String... format) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.LE);
        }
        Date date = SearchUtil.toDate(value.toString(), format);
        if (date == null) {
            return wherePlaceholder(key, EnumOperator.LE);
        }
        return le(key, date);
    }

    /**
     * 小于等于 不允许为空值
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable le(String key, Number value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.LE);
        }
        return where(key, EnumOperator.LE, value);
    }

    /**
     * 小于等于
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable le(String key, Date value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.LE);
        }
        return where(key, EnumOperator.LE, value);
    }

    /**
     * 小于等于 会自动转换为时间格式
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable le(String key, String value) {
        return le(key, value, DEFAULT_FORMATS);
    }


    /**
     * 大于 转换为时间格式
     *
     * @param key
     * @param value
     * @param format
     * @return
     */
    public Searchable gt(String key, CharSequence value, String... format) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.GT);
        }
        Date date = SearchUtil.toDate(value.toString(), format);
        if (date == null) {
            return wherePlaceholder(key, EnumOperator.GT);
        }
        return gt(key, date);
    }

    /**
     * 大于
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable gt(String key, Number value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.GT);
        }
        return where(key, EnumOperator.GT, value);
    }

    /**
     * 大于
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable gt(String key, Date value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.GT);
        }
        return where(key, EnumOperator.GT, value);
    }

    /**
     * 大于 会自动转换为时间格式
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable gt(String key, String value) {
        return gt(key, value, DEFAULT_FORMATS);
    }

    /**
     * 大于 转换为时间格式
     *
     * @param key
     * @param value
     * @param format
     * @return
     */
    public Searchable ge(String key, CharSequence value, String... format) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.GE);
        }
        Date date = SearchUtil.toDate(value.toString(), format);
        if (date == null) {
            return wherePlaceholder(key, EnumOperator.GE);
        }
        return ge(key, date);
    }

    /**
     * 大于
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable ge(String key, Number value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.GE);
        }
        return where(key, EnumOperator.GE, value);
    }

    /**
     * 大于
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable ge(String key, Date value) {
        if (value == null) {
            return wherePlaceholder(key, EnumOperator.GE);
        }
        return where(key, EnumOperator.GE, value);
    }

    /**
     * 大于 会自动转换为时间格式
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable ge(String key, String value) {
        return ge(key, value, DEFAULT_FORMATS);
    }

    /**
     * In 查询
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable in(String key, Object... value) {
        if (value != null && value.length > 0) {
            return where(key, EnumOperator.IN, Arrays.asList(value));
        }
        return wherePlaceholder(key, EnumOperator.IN);
    }

    /**
     * In 查询
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable in(String key, Collection<?> value) {
        if (CollectionUtils.isNotEmpty(value)) {
            return where(key, EnumOperator.IN, new ArrayList<>(value));
        }
        return wherePlaceholder(key, EnumOperator.IN);
    }

    /**
     * Not In
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable notIn(String key, Object... value) {
        if (value != null && value.length > 0) {
            return where(key, EnumOperator.NOT_IN, Arrays.asList(value));
        }
        return wherePlaceholder(key, EnumOperator.NOT_IN);
    }

    /**
     * Not In 查询
     *
     * @param key
     * @param value
     * @return
     */
    public Searchable notIn(String key, Collection<?> value) {
        if (CollectionUtils.isNotEmpty(value)) {
            return where(key, EnumOperator.NOT_IN, new ArrayList<>(value));
        }
        return wherePlaceholder(key, EnumOperator.NOT_IN);
    }

    /**
     * 介于
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Searchable between(String key, Date start, Date end) {
        if (start == null || end == null) {
            return wherePlaceholder(key, EnumOperator.BETWEEN);
        }
        if (end.compareTo(start) < 0) {
            // 开始时间不能小于结束时间
            return wherePlaceholder(key, EnumOperator.BETWEEN);
        }
        Date[] dates = {start, end};
        return where(key, EnumOperator.BETWEEN, dates);
    }

    /**
     * 介于
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Searchable between(String key, Number start, Number end) {
        if (start == null || end == null) {
            return wherePlaceholder(key, EnumOperator.BETWEEN);
        }
        if (end.longValue() < start.longValue()) {
            return wherePlaceholder(key, EnumOperator.BETWEEN);
        }
        Number[] numbers = {start, end};
        return where(key, EnumOperator.BETWEEN, numbers);
    }

    /**
     * 介于 会转换为时间格式
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Searchable between(String key, String start, String end, String... formats) {
        return between(key, SearchUtil.toDate(start, formats), SearchUtil.toDate(end, formats));
    }

    /**
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Searchable between(String key, String start, String end) {
        return between(key, start, end, DEFAULT_FORMATS);
    }

    /**
     * 字段为空
     *
     * @param key
     * @return
     */
    public Searchable isNull(String key) {
        return where(key, EnumOperator.IS_NULL);
    }

    /**
     * 字段不为空
     *
     * @param key
     * @return
     */
    public Searchable isNotNull(String key) {
        return where(key, EnumOperator.IS_NOT_NULL);
    }

    /**
     * 为空 或者空字符串
     *
     * @param key
     * @return
     */
    public Searchable isBlank(String key) {
        return where(key, EnumOperator.IS_BLANK);
    }

    /**
     * 不为空 或者空字符串
     *
     * @param key
     * @return
     */
    public Searchable isNotBlank(String key) {
        return where(key, EnumOperator.IS_NOT_BLANK);
    }

    /**
     * 模糊查询 需要加%号
     *
     * @param key
     * @param str
     * @return
     */
    public Searchable like(String key, Object str, boolean auto) {
        if (str == null) {
            return wherePlaceholder(key, EnumOperator.LIKE);
        }
        String obj = StringUtils.trim(str.toString());
        if (StringUtils.isBlank(obj)) {
            return wherePlaceholder(key, EnumOperator.LIKE);
        }
        if (auto) {
            if (obj.startsWith("%") || obj.endsWith("%")) {
                return where(key, EnumOperator.LIKE, obj);
            }
            return where(key, EnumOperator.LIKE, "%" + obj + "%");
        }

        return where(key, EnumOperator.LIKE, obj);
    }

    /**
     * 模糊匹配
     *
     * @param key
     * @param str
     * @return
     */
    public Searchable like(String key, Object str) {
        return like(key, str, true);
    }

    /**
     * 模糊不匹配 注意:有性能风险
     *
     * @param key
     * @param str
     * @return
     */
    public Searchable notLike(String key, Object str, boolean auto) {
        if (str == null) {
            return wherePlaceholder(key, EnumOperator.NOT_LIKE);
        }
        String obj = StringUtils.trim(str.toString());
        if (StringUtils.isBlank(obj)) {
            return wherePlaceholder(key, EnumOperator.NOT_LIKE);
        }
        if (auto) {
            if (obj.startsWith("%") || obj.endsWith("%")) {
                return where(key, EnumOperator.NOT_LIKE, obj);
            }
            return where(key, EnumOperator.NOT_LIKE, "%" + obj + "%");
        }

        return where(key, EnumOperator.NOT_LIKE, obj);
    }

    /**
     * 模糊匹配
     *
     * @param key
     * @param str
     * @return
     */
    public Searchable notLike(String key, Object str) {
        return notLike(key, str, true);
    }

    /**
     * 数据集去重
     *
     * @param distinct
     * @return
     */
    public Searchable distinct(boolean distinct) {
        this.config.setDistinct(distinct);
        return this;
    }

    /**
     * 数据集去重
     *
     * @return
     */
    public Searchable distinct() {
        this.config.setDistinct(true);
        return this;
    }



    /**
     * @return
     */
    public BuildFactory build() {
        return new BuildFactory(this.config.getConditions());
    }


    @Override
    public String toString() {
        return JsonUtil.toJson(this.config.getConditions());
    }
}