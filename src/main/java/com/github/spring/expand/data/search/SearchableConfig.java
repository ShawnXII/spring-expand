package com.github.spring.expand.data.search;

import com.github.spring.expand.data.search.cache.ClassField;
import com.github.spring.expand.data.search.condition.Condition;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索 配置
 *
 * @author wx
 * @date 2020/12/11 9:53
 */
public final class SearchableConfig {

    public static final String AND_JOIN = "and";

    public static final String OR_JOIN = "or";

    private String join = AND_JOIN; // 当前查询 and / or
    /**
     * 查询条件
     */
    private List<Condition> conditions = new ArrayList<>();
    /**
     * 当前查询条件
     */
    private Condition condition;

    private Condition currentCondition;

    private final ClassField classField;

    private boolean distinct = false;

    public SearchableConfig(ClassField classField) {
        this.classField = classField;
    }

    /**
     * and连接
     */
    public void and() {
        if (!StringUtils.equalsIgnoreCase(join, AND_JOIN)) {
            this.setJoin(AND_JOIN);
        }
    }

    public void or() {
        if (!StringUtils.equalsIgnoreCase(join, OR_JOIN)) {
            this.setJoin(OR_JOIN);
            return;
        }
        this.condition = this.currentCondition;
    }

    /**
     * @param condition
     */
    public void add(Condition condition) {
        this.currentCondition = condition;
        // And 查询
        if (StringUtils.equalsIgnoreCase(this.join, AND_JOIN)) {
            this.condition = condition;
            conditions.add(this.condition);
            return;
        }
        // OR 查询
        if (this.condition == null) {
            this.condition = condition;
            conditions.add(this.condition);
            return;
        }
        this.condition.or(condition);
    }

    /**
     * 是否有效
     */
    public void invalidQuery() {
        if (this.condition != null) {
            this.condition.addCheckValue(Condition.FIELD_NOT_BLANK);
        }
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public ClassField getClassField() {
        return classField;
    }
}
