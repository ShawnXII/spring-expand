package com.github.spring.expand.data.search.condition;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * 字段校验器
 *
 * @author wx
 * @date 2020/12/7 16:37
 */
public interface CheckValue {
    /**
     * @param value
     * @return
     */
    Boolean check(Condition condition, List<Predicate> pl);
}
