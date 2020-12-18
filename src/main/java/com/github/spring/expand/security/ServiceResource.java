package com.github.spring.expand.security;

import java.io.Serializable;

/**
 * 接口资源
 *
 * @author wx
 * @date 2020/12/17 10:30
 */
public class ServiceResource implements Serializable {

    // 系统名称
    private String applicationName;
    // 资源名称
    private String name;
    // 唯一编码
    private String code;
    // 请求方式 get/post/put/delete/patch
    private String requestMethodName;
    // url地址
    private String url;
    // 包名
    private String packageName;
    // 类名称
    private String className;
    // 方法名称
    private String methodName;
    // 是否需要登录访问
    private Boolean login;
    // 登录账号锁定后是否可以访问 login = true 才会生效
    private Boolean lock;
    // 登录账号禁用后是否可以访问 login = true 才会生效
    private Boolean forbidden;
    // 接口是否是基本接口
    private Boolean base;
    // 是否需要管控权限
    private Boolean role;
    // 是否进行访问限制 (可以根据角色设置)
    private Boolean limit;
    // 限制次数
    private Integer limitNumber;
    // 保留时间
    private Integer retentionTime;
    // 锁定时间
    private Long lockTime;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestMethodName() {
        return requestMethodName;
    }

    public void setRequestMethodName(String requestMethodName) {
        this.requestMethodName = requestMethodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Boolean getForbidden() {
        return forbidden;
    }

    public void setForbidden(Boolean forbidden) {
        this.forbidden = forbidden;
    }

    public Boolean getBase() {
        return base;
    }

    public void setBase(Boolean base) {
        this.base = base;
    }

    public Boolean getRole() {
        return role;
    }

    public void setRole(Boolean role) {
        this.role = role;
    }

    public Boolean getLimit() {
        return limit;
    }

    public void setLimit(Boolean limit) {
        this.limit = limit;
    }

    public Integer getLimitNumber() {
        return limitNumber;
    }

    public void setLimitNumber(Integer limitNumber) {
        this.limitNumber = limitNumber;
    }

    public Integer getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Integer retentionTime) {
        this.retentionTime = retentionTime;
    }

    public Long getLockTime() {
        return lockTime;
    }

    public void setLockTime(Long lockTime) {
        this.lockTime = lockTime;
    }
}
