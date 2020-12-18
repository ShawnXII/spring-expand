package com.github.spring.expand.login.service;

import com.github.spring.expand.login.dto.LoginDto;

/**
 * @author wx
 * @date 2020/12/17 16:43
 */
public interface LoginService {
    /**
     * 用户登录
     */
    void login(LoginDto loginDto);

}
