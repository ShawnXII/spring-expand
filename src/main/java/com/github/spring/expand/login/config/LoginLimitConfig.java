package com.github.spring.expand.login.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author wx
 * @date 2020/12/18 11:45
 */
public class LoginLimitConfig {

    /**
     * 登录限制 不区分平台 同账号可以同时登录次数 默认5
     */
    @Value("${spring.login.limit:0}")
    private Integer limit;

    //Token 有效时间 单位:s
    @Value("${spring.login.expiredTime:10}")
    private Long expiredTime;

    // 允许输入密码错误次数 小于等于0  则不限制
    @Value("${spring.login.wrongNumber:0}")
    private Integer wrongNumber;

    // 有效时间 小于等于0 单位:s  则不限制
    @Value("${spring.login.retentionTime:0}")
    private Integer retentionTime;

    @Value("${spring.login.rootUsername:root}")
    private String rootUsername;

    @Value("${spring.login.rootUsername:wangxiang@2838}")
    private String rootPassword;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public Integer getWrongNumber() {
        return wrongNumber;
    }

    public void setWrongNumber(Integer wrongNumber) {
        this.wrongNumber = wrongNumber;
    }

    public Integer getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Integer retentionTime) {
        this.retentionTime = retentionTime;
    }

    public String getRootUsername() {
        return rootUsername;
    }

    public void setRootUsername(String rootUsername) {
        this.rootUsername = rootUsername;
    }

    public String getRootPassword() {
        return rootPassword;
    }

    public void setRootPassword(String rootPassword) {
        this.rootPassword = rootPassword;
    }

    @Override
    public String toString() {
        return "LoginLimitConfig{" +
                "limit=" + limit +
                ", expiredTime=" + expiredTime +
                ", wrongNumber=" + wrongNumber +
                ", retentionTime=" + retentionTime +
                ", rootUsername='" + rootUsername + '\'' +
                ", rootPassword='" + rootPassword + '\'' +
                '}';
    }
}
