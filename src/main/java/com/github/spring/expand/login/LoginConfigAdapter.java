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
     * 同一账号可以登录机器数量 默认:5
     *
     * @return
     */
    public LoginConfigAdapter limit() {
        return limit(5);
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
     * 设置密码可以输入错误次数 默认可以输入错误次数 5次
     *
     * @return
     */
    public LoginConfigAdapter wrongNumber() {
        return wrongNumber(5);
    }

    /**
     * 设置密码可以输入错误次数 小于等于0 则不限制错误次数
     *
     * @param wrongNumber
     * @return
     */
    public LoginConfigAdapter wrongNumber(Integer wrongNumber) {
        this.loginConfig.setWrongNumber(wrongNumber);
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

    @Override
    public String toString() {
        return "LoginConfigAdapter{" +
                "loginConfig=" + loginConfig +
                ", loginService=" + loginService +
                '}';
    }
}
