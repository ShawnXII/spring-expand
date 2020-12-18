package com.github.spring.expand.data.search.build;

import com.github.spring.expand.data.search.condition.Condition;

import java.util.List;

/**
 * @author wx
 * @date 2020/12/11 17:27
 */
public class SqlBuild implements Build<String>{

    public SqlBuild() {
    }

    /**
     * 生成
     *
     * @param conditions
     * @return
     */
    @Override
    public String build(List<Condition> conditions) {
        return null;
    }
}
