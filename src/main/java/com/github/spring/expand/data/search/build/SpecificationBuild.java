package com.github.spring.expand.data.search.build;

import com.github.spring.expand.data.search.condition.Condition;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wx
 * @date 2020/12/11 17:28
 */
public class SpecificationBuild implements Build<Specification> {

    /**
     * 生成
     *
     * @param conditions
     * @return
     */
    @Override
    public Specification build(List<Condition> conditions) {

        return (root, query, cb) -> {
            List<Predicate> ands = new ArrayList<>();
            conditions.stream().forEach(condition -> {
                Predicate p = BuildUtils.toPredicate(condition, root, query, cb);
                if (p != null) {
                    ands.add(p);
                }
            });
            if (CollectionUtils.isNotEmpty(ands)) {
                return cb.and(ands.toArray(new Predicate[ands.size()]));
            }
            return null;
        };
    }


}
