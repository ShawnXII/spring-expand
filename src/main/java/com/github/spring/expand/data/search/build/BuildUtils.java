package com.github.spring.expand.data.search.build;

import com.github.spring.expand.data.search.cache.ClassField;
import com.github.spring.expand.data.search.cache.ClassFieldInfo;
import com.github.spring.expand.data.search.condition.Condition;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wx
 * @date 2020/12/14 8:56
 */
public class BuildUtils {

    BuildUtils() {
    }

    public static Path<?> getPath(Condition condition, Root<?> root) {
        String key = condition.getKey();
        String[] arr = StringUtils.split(key, ".");
        return getPath(arr, condition, root);
    }

    /**
     * @param condition
     * @param path
     * @param criteriaBuilder
     * @return
     */
    private static Predicate toPredicate(Condition condition, Path<?> path, CriteriaBuilder criteriaBuilder) {
        switch (condition.getOperator()) {
            case EQ:
                // 等于
                return eq(path, condition, criteriaBuilder);
            case NE:
                // 不等于
                return ne(path, condition, criteriaBuilder);
            case GE:
                return ge(path, condition, criteriaBuilder);
            case GT:
                return gt(path, condition, criteriaBuilder);
            case LE:
                return le(path, condition, criteriaBuilder);
            case LT:
                return lt(path, condition, criteriaBuilder);
            case BETWEEN:
                return between(path, condition, criteriaBuilder);
            case LIKE:
                return like(path, condition, criteriaBuilder);
            case NOT_LIKE:
                return notLike(path, condition, criteriaBuilder);
            case IN:
                return in(path, condition, criteriaBuilder);
            case NOT_IN:
                return notIn(path, condition, criteriaBuilder);
            case IS_NULL:
                return isNull(path, criteriaBuilder);
            case IS_BLANK:
                return isBlank(path, criteriaBuilder);
            case IS_NOT_NULL:
                return isNotNull(path, criteriaBuilder);
            case IS_NOT_BLANK:
                return isNotBlank(path, criteriaBuilder);
            default:
                return null;
        }
    }

    /**
     * @param condition
     * @param root
     * @param query
     * @param criteriaBuilder
     * @return
     */
    public static Predicate toPredicate(Condition condition, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (!condition.isValid()) {
            return null;
        }
        Path<?> path = getPath(condition, root);
        if (path == null) {
            return null;
        }
        Predicate p = toPredicate(condition, path, criteriaBuilder);
        List<Predicate> pl = new ArrayList<>();
        if (p != null) {
            pl.add(p);
        }
        List<Condition> ors = condition.getOrCondition();
        if (CollectionUtils.isNotEmpty(ors)) {
            ors.stream().forEach(c -> {
                Predicate p1 = toPredicate(c, root, query, criteriaBuilder);
                if (p1 != null) {
                    pl.add(p1);
                }
            });
        }
        if (CollectionUtils.isNotEmpty(pl)) {
            if (pl.size() > 1) {
                return criteriaBuilder.or(pl.toArray(new Predicate[pl.size()]));
            }
            return pl.get(0);
        }
        return null;
    }


    /**
     * @param path
     * @param criteriaBuilder
     * @return
     */
    private static Predicate isBlank(Path<?> path, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or(criteriaBuilder.isNull(path), criteriaBuilder.equal(path, ""));
    }

    /**
     * @param path
     * @param criteriaBuilder
     * @return
     */
    private static Predicate isNotBlank(Path<?> path, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(criteriaBuilder.isNotNull(path), criteriaBuilder.notEqual(path, ""));
    }


    /**
     * IS_NULL
     *
     * @param path
     * @param criteriaBuilder
     * @return
     */
    private static Predicate isNull(Path<?> path, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNull(path);
    }

    /**
     * IS_NOT_NULL
     *
     * @param path
     * @param criteriaBuilder
     * @return
     */
    private static Predicate isNotNull(Path<?> path, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.isNotNull(path);
    }

    /**
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate notIn(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (CollectionUtils.isNotEmpty(condition.getListValue())) {
            CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
            condition.getListValue().stream().forEach(v -> {
                in.value(v);
            });
            return criteriaBuilder.not(in);
        }
        return null;
    }

    /**
     * IN 查询
     *
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate in(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (CollectionUtils.isNotEmpty(condition.getListValue())) {
            CriteriaBuilder.In<Object> in = criteriaBuilder.in(path);
            condition.getListValue().stream().forEach(v -> {
                in.value(v);
            });
            return in;
        }
        return null;
    }

    /**
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate like(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        String ls = toStringValue(condition);
        if (StringUtils.isNotBlank(ls)) {
            return criteriaBuilder.like(path.as(String.class), ls);
        }
        return null;
    }

    /**
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate notLike(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        String ls = toStringValue(condition);
        if (StringUtils.isNotBlank(ls)) {
            return criteriaBuilder.notLike(path.as(String.class), ls);
        }
        return null;
    }

    /**
     * @param condition
     * @return
     */
    private static String toStringValue(Condition condition) {
        String ls = "";
        if (StringUtils.isNotBlank(condition.getStrValue())) {
            ls = StringUtils.trim(condition.getStrValue().toString());
        } else if (condition.getNumberValue() != null) {
            ls = StringUtils.trim(condition.getNumberValue().toString());
        } else if (condition.getObjValue() != null) {
            ls = StringUtils.trim(condition.getObjValue().toString());
        }
        return ls;
    }

    /**
     * 介于
     *
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate between(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (condition.getBetweenDate() != null && condition.getBetweenDate().length == 2) {
            return criteriaBuilder.between(path.as(Date.class), condition.getBetweenDate()[0], condition.getBetweenDate()[1]);
        }
        if (condition.getBetweenNumber() != null && condition.getBetweenNumber().length == 2) {
            return criteriaBuilder.and(criteriaBuilder.gt(path.as(Number.class), condition.getBetweenNumber()[0]), criteriaBuilder.lt(path.as(Number.class), condition.getBetweenNumber()[1]));
        }
        return null;
    }

    /**
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate gt(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (condition.getNumberValue() != null) {
            return criteriaBuilder.gt(path.as(Number.class), condition.getNumberValue());
        }
        if (condition.getDateValue() != null) {
            return criteriaBuilder.greaterThan(path.as(Date.class), condition.getDateValue());
        }
        return null;
    }

    /**
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate ge(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (condition.getNumberValue() != null) {
            return criteriaBuilder.ge(path.as(Number.class), condition.getNumberValue());
        }
        if (condition.getDateValue() != null) {
            return criteriaBuilder.greaterThanOrEqualTo(path.as(Date.class), condition.getDateValue());
        }
        return null;
    }

    /**
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate le(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (condition.getNumberValue() != null) {
            return criteriaBuilder.le(path.as(Number.class), condition.getNumberValue());
        }
        if (condition.getDateValue() != null) {
            return criteriaBuilder.lessThanOrEqualTo(path.as(Date.class), condition.getDateValue());
        }
        return null;
    }

    /**
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate lt(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (condition.getNumberValue() != null) {
            return criteriaBuilder.lt(path.as(Number.class), condition.getNumberValue());
        }
        if (condition.getDateValue() != null) {
            return criteriaBuilder.lessThan(path.as(Date.class), condition.getDateValue());
        }
        return null;
    }

    /**
     * 等于
     *
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate eq(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isNotBlank(condition.getStrValue())) {
            return criteriaBuilder.equal(path, condition.getStrValue());
        }
        if (condition.getNumberValue() != null) {
            return criteriaBuilder.equal(path, condition.getNumberValue());
        }
        if (condition.getDateValue() != null) {
            return criteriaBuilder.equal(path, condition.getDateValue());
        }
        if (condition.getObjValue() != null) {
            return criteriaBuilder.equal(path, condition.getObjValue());
        }
        return null;
    }

    /**
     * 不等于
     *
     * @param path
     * @param condition
     * @param criteriaBuilder
     * @return
     */
    private static Predicate ne(Path<?> path, Condition condition, CriteriaBuilder criteriaBuilder) {
        if (StringUtils.isNotBlank(condition.getStrValue())) {
            return criteriaBuilder.notEqual(path, condition.getStrValue());
        }
        if (condition.getNumberValue() != null) {
            return criteriaBuilder.notEqual(path, condition.getNumberValue());
        }
        if (condition.getDateValue() != null) {
            return criteriaBuilder.notEqual(path, condition.getDateValue());
        }
        if (condition.getObjValue() != null) {
            return criteriaBuilder.notEqual(path, condition.getObjValue());
        }
        return null;
    }

    /**
     * @param arr
     * @param condition
     * @return
     */
    private static Path<?> getPath(String[] arr, Condition condition, Root<?> root) {
        int len = arr.length;
        if (len == 1) {

            ClassFieldInfo classFieldInfo = condition.getClassField().getFieldInfo(arr[0]);
            if (classFieldInfo != null) {
                return root.get(classFieldInfo.getFieldName());
            }
            return null;
        }
        // 关联查询
        Path<?> join = null;
        ClassFieldInfo classFieldInfo = null;
        for (int i = 0; i < len; i++) {
            String str = arr[i];
            if (classFieldInfo == null) {
                classFieldInfo = condition.getClassField().getFieldInfo(str);
            }
            if (classFieldInfo == null) {
                return null;
            }
            // 最后一个了
            if ((i + 1) == len) {
                if (join != null) {
                    return join.get(classFieldInfo.getFieldName());
                }
                return null;
            }
            join = getJoin(classFieldInfo, root);
            classFieldInfo = getFieldInfoByList(classFieldInfo.getAssociationProperty(), arr[i + 1]);
        }
        return null;
    }

    /**
     * @param classFieldInfo
     * @param root
     * @return
     */
    private static Path<?> getJoin(ClassFieldInfo classFieldInfo, Root<?> root) {
        if (classFieldInfo != null) {
            // 转换失败
            if (!classFieldInfo.getAssociation()) {
                return null;
            }
            if (!classFieldInfo.isTransition()) {
                // 转换失败了 直接抛弃
                return null;
            }
            if (classFieldInfo.getOneToOne()) {
                // 一对一查询
                return root.join(classFieldInfo.getFieldName(), JoinType.INNER);
            }
            if (classFieldInfo.getManyToMany()) {
                return root.join(classFieldInfo.getFieldName(), JoinType.LEFT);
            }
            if (classFieldInfo.getManyToOne()) {
                return root.join(classFieldInfo.getFieldName(), JoinType.LEFT);
            }
            if (classFieldInfo.getOneToMany()) {
                return root.join(classFieldInfo.getFieldName(), JoinType.LEFT);
            }
        }
        return null;
    }

    /**
     * @param classFieldInfoList
     * @param key
     * @return
     */
    private static ClassFieldInfo getFieldInfoByList(List<ClassFieldInfo> classFieldInfoList, String key) {
        for (ClassFieldInfo field : classFieldInfoList) {
            String fieldName = field.getFieldName();
            String dbFieldName = field.getDbFieldName();
            if (StringUtils.equalsIgnoreCase(key, fieldName) || StringUtils.equalsIgnoreCase(key, dbFieldName)) {
                return field;
            }
        }
        return null;
    }
}
