package com.github.spring.expand.login;

import com.github.spring.expand.login.config.LoginConfig;
import com.github.spring.expand.login.config.RootAccount;
import com.github.spring.expand.login.service.LoginService;
import com.github.spring.expand.util.SpringBeanUtils;

/**
 * 登录适配器
 *
 * @author wx
 * @date 2020/12/17 11:03
 */
public class LoginConfigAdapter {
    // 登录配置
    private LoginConfig loginConfig;
    // 登录接口
    private LoginService loginService;


    private LoginConfigAdapter() {
        this.loginConfig = SpringBeanUtils.getBean(LoginConfig.class);
    }


    public static LoginConfigAdapter getInstance() {
        return new LoginConfigAdapter();
    }

    /**
     * 登录协议  .protocol().token() 或者 protocol().cookie() 或者 protocol().oAuth2()
     *
     * @return
     */
    public LoginProtocol protocol() {
        return new LoginProtocol(this);
    }

    /**
     * 设置自定义的登录协议
     *
     * @return
     */
    public LoginConfigAdapter setProtocol(LoginService loginService) {
        this.loginService = loginService;
        return this;
    }


    /**
     * 设置同一账号可以登录机器数量 小于等于0 或者大于99 则不限制
     *
     * @param limit
     * @return
     */
    public LoginConfigAdapter limit(Integer limit) {
        this.loginConfig.setLimit(limit);
        return this;
    }


    /**
     * 设置密码可以输入错误次数 小于等于0 或者大于99 则不限制错误次数
     *
     * @param wrongNumber
     * @return
     */
    public LoginConfigAdapter wrongNumber(Integer wrongNumber) {
        this.loginConfig.setWrongNumber(wrongNumber);
        return this;
    }

    /**
     * 设置token过期时间
     *
     * @param expiredTime 过期时间 单位:s
     * @return
     */
    public LoginConfigAdapter expiredTime(Long expiredTime) {
        this.loginConfig.setExpiredTime(expiredTime);
        return this;
    }

    /**
     * 设置账号锁定时间(密码输入错误 超过次数后的锁定时间)
     *
     * @param lockTime 锁定时间 单位:s
     * @return
     */
    public LoginConfigAdapter lockTime(Long lockTime) {
        this.loginConfig.setLockTime(lockTime);
        return this;
    }

    /**
     * 新增管理员账号
     *
     * @param username
     * @param password
     * @return
     */
    public LoginConfigAdapter addRoot(String username, String password) {
        this.loginConfig.addRoot(new RootAccount(username, password));
        return this;
    }

    /**
     * 新增登录平台
     *
     * @param code
     * @param name
     * @return
     */
    public LoginConfigAdapter addPlatform(String code, String name) {

        return this;
    }


    @Override
    public String toString() {
        return "LoginConfigAdapter{" +
                "loginConfig=" + loginConfig +
                ", loginService=" + loginService +
                '}';
    }
}
