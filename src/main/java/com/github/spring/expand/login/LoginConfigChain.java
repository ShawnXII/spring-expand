package com.github.spring.expand.login;

/**
 * 链条
 *
 * @author wx
 * @date 2020/12/18 8:34
 */
public abstract class LoginConfigChain {

    protected final LoginConfigAdapter adapter;

    public LoginConfigChain(LoginConfigAdapter adapter) {
        this.adapter = adapter;
    }

    public LoginConfigAdapter end() {
        return this.adapter;
    }
}
