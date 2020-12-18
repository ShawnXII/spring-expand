package com.github.spring.expand.data.search.condition;

import com.github.spring.expand.data.search.cache.ClassField;
import com.github.spring.expand.data.search.cache.ClassFieldInfo;
import com.github.spring.expand.data.search.operator.EnumOperator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 细分了查询值的类型 Number,Date,CharSequence 要避免出现无效的sql查询条件
 *
 * @author wx
 * @date 2020/12/7 16:29
 */
public class Condition implements Serializable {
    /**
     * 允许部分为空 但是不能全部为空
     */
    public static CheckValue FIELD_NOT_BLANK = ((condition, pls) -> {
        if (CollectionUtils.isNotEmpty(pls)) {
            return true;
        }
        return false;
    });
    /**
     * 全部不为空, 不允许部分为空的
     */
    public static CheckValue FIELD_ALL_NOT_BLANK = ((condition, pls) -> {
        if (CollectionUtils.isEmpty(pls)) {
            return false;
        }
        int len = condition.getOrCondition().size() + 1;
        return len == pls.size();
    });
    /**
     * key
     */
    private final String key;
    /**
     * Number 类型的值
     */
    private Number numberValue;
    /**
     * 字符串类型的值
     */
    private transient CharSequence strValue;
    /**
     * 时间类型的值
     */
    private Date dateValue;
    /**
     * Object类型
     */
    private transient Object objValue;
    /**
     * 数组类型
     */
    private transient List<Object> listValue;

    private Date[] betweenDate;

    private Number[] betweenNumber;
    /**
     * 连接器
     */
    private EnumOperator operator;
    /**
     *
     */
    private ClassFieldInfo classFieldInfo;
    /**
     * Or 查询
     */
    private List<Condition> orCondition = new ArrayList<>();
    /**
     * 校验器
     */
    private List<CheckValue> checkValues = new ArrayList<>();

    private ClassField classField;


    private boolean valid = true; // 是否有效




    /**
     * 创建连接器
     *
     * @param key
     */
    public Condition(String key, EnumOperator operator, ClassField classField) {
        this.key = key;
        this.operator = operator;
        this.classField = classField;
    }


    /**
     * @return
     */
    public boolean isBlank() {
        if (this.getDateValue() != null || this.getNumberValue() != null || StringUtils.isNotBlank(this.getStrValue()) || this.getObjValue() != null) {
            return false;
        }
        if (CollectionUtils.isNotEmpty(this.getListValue())) {
            return false;
        }
        if (this.getBetweenNumber() != null && this.getBetweenNumber().length == 2) {
            return false;
        }
        if (this.getBetweenDate() != null && this.getBetweenDate().length == 2) {
            return false;
        }
        return true;
    }

    public void addCheckValue(CheckValue cv) {
        this.checkValues.add(cv);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * @param condition
     */
    public void or(Condition condition) {
        this.orCondition.add(condition);
    }


    /**
     * 添加校验
     *
     * @param checkValue
     */
    public void addCheck(CheckValue checkValue) {
        this.checkValues.add(checkValue);
    }

    public List<Object> getListValue() {
        return listValue;
    }

    public void setListValue(List<Object> listValue) {
        this.listValue = listValue;
    }

    public String getKey() {
        return key;
    }

    public Date[] getBetweenDate() {
        return betweenDate;
    }

    public void setBetweenDate(Date[] betweenDate) {
        this.betweenDate = betweenDate;
    }

    public Number[] getBetweenNumber() {
        return betweenNumber;
    }

    public void setBetweenNumber(Number[] betweenNumber) {
        this.betweenNumber = betweenNumber;
    }

    public Number getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(Number numberValue) {
        this.numberValue = numberValue;
    }

    public CharSequence getStrValue() {
        return strValue;
    }

    public void setStrValue(CharSequence strValue) {
        this.strValue = strValue;
    }

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public Object getObjValue() {
        return objValue;
    }

    public void setObjValue(Object objValue) {
        this.objValue = objValue;
    }

    public EnumOperator getOperator() {
        return operator;
    }

    public void setOperator(EnumOperator operator) {
        this.operator = operator;
    }

    public ClassFieldInfo getClassFieldInfo() {
        return classFieldInfo;
    }

    public void setClassFieldInfo(ClassFieldInfo classFieldInfo) {
        this.classFieldInfo = classFieldInfo;
    }

    public List<Condition> getOrCondition() {
        return orCondition;
    }

    public void setOrCondition(List<Condition> orCondition) {
        this.orCondition = orCondition;
    }

    public List<CheckValue> getCheckValues() {
        return checkValues;
    }

    public void setCheckValues(List<CheckValue> checkValues) {
        this.checkValues = checkValues;
    }

    public ClassField getClassField() {
        return classField;
    }

    public void setClassField(ClassField classField) {
        this.classField = classField;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "key='" + key + '\'' +
                ", numberValue=" + numberValue +
                ", strValue=" + strValue +
                ", dateValue=" + dateValue +
                ", objValue=" + objValue +
                ", listValue=" + listValue +
                ", betweenDate=" + Arrays.toString(betweenDate) +
                ", betweenNumber=" + Arrays.toString(betweenNumber) +
                ", operator=" + operator +
                ", classFieldInfo=" + classFieldInfo +
                ", orCondition=" + orCondition +
                ", checkValues=" + checkValues +
                ", classField=" + classField +
                ", valid=" + valid +
                '}';
    }
}
