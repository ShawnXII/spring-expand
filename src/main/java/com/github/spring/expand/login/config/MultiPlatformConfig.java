package com.github.spring.expand.login.config;

/**
 * 多平台配置
 *
 * @author wx
 * @date 2020/12/18 11:01
 */
public class MultiPlatformConfig {

    // 平台编码
    private String code;
    // 平台名称
    private String name;
    // 平台登录配置
    private LoginLimitConfig loginLimitConfig;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginLimitConfig getLoginLimitConfig() {
        return loginLimitConfig;
    }

    public void setLoginLimitConfig(LoginLimitConfig loginLimitConfig) {
        this.loginLimitConfig = loginLimitConfig;
    }
}
