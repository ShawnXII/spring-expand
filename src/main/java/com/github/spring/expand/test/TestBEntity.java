package com.github.spring.expand.test;


import com.github.spring.expand.base.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author wx
 * @date 2020/12/15 18:46
 */
@javax.persistence.Entity
@Table(name = "wx_search_test_b")
public class TestBEntity  implements Entity<Long> {

    @Id
    private Long id;


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
