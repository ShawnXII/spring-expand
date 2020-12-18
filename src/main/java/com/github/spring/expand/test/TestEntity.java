package com.github.spring.expand.test;

import com.github.spring.expand.base.Entity;

import javax.persistence.*;

/**
 * @author wx
 * @date 2020/12/11 16:28
 */
@javax.persistence.Entity
@Table(name = "wx_search_test")
public class TestEntity implements Entity<Long> {

    @Id
    private Long id;

    private String name;

    private String title;

    private String key1;

    private String key2;

    private String key3;

    private boolean flag;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "test_b_id", referencedColumnName = "id", nullable = true)
    private TestBEntity testBEntity;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public TestBEntity getTestBEntity() {
        return testBEntity;
    }

    public void setTestBEntity(TestBEntity testBEntity) {
        this.testBEntity = testBEntity;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", key1='" + key1 + '\'' +
                ", key2='" + key2 + '\'' +
                ", key3='" + key3 + '\'' +
                ", flag=" + flag +
                ", testBEntity=" + testBEntity +
                '}';
    }
}
