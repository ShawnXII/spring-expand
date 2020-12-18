package com.github.spring.expand.login;

import com.github.spring.expand.login.service.TokenLoginServiceImpl;
import com.github.spring.expand.util.SpringBeanUtils;

/**
 * @author wx
 * @date 2020/12/17 11:06
 */
public class LoginProtocol extends LoginConfigChain {

    public LoginProtocol(LoginConfigAdapter adapter) {
        super(adapter);
    }

    /**
     * token 协议登录 只能选择一样
     *
     * @return
     */
    public TokenConfigChain token() {
        super.adapter.setProtocol(SpringBeanUtils.registerBean(TokenLoginServiceImpl.class));
        return SpringBeanUtils.registerBean("tokenConfig", TokenConfigChain.class, this.adapter);
    }

    /**
     * OAuth2 协议 支持 只能选择一样
     *
     * @return
     */
    public LoginConfigAdapter oAuth2() {
        super.adapter.setProtocol(SpringBeanUtils.registerBean(TokenLoginServiceImpl.class));
        return super.adapter;
    }

    /**
     * Cookie登录 只能选择一样
     *
     * @return
     */
    public LoginConfigAdapter cookie() {
        return super.adapter;
    }

}
