package com.github.spring.expand.base;

import java.io.Serializable;

/**
 * Entity
 *
 * @author wx
 * @date 2020/12/9 11:55
 */
public interface Entity<ID extends Serializable> extends Serializable {
    /**
     * 获取主键
     *
     * @return
     */
    ID getId();

    /**
     * 设置主键
     *
     * @param id
     */
    void setId(ID id);
}
