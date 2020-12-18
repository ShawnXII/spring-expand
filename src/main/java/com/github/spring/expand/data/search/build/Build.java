package com.github.spring.expand.data.search.build;

import com.github.spring.expand.data.search.condition.Condition;

import java.util.List;

/**
 * @author wx
 * @date 2020/12/11 17:25
 */
public interface Build<T> {
    /**
     * 生成
     *
     * @param conditions
     * @return
     */
    T build(List<Condition> conditions);
}
