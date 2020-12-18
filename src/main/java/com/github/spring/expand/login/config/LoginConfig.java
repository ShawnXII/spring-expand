package com.github.spring.expand.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wx
 * @date 2020/12/18 11:36
 */
@Component
public class LoginConfig extends LoginLimitConfig {

    @PostConstruct
    private void init() {
        this.addRoot(new RootAccount(super.getRootUsername(), super.getRootPassword()));
    }

    // 多平台登录 开启后同账号可以同时在多端登录 否则只能在一个平台上登录
    @Value("${spring.login.multiPlatform:true}")
    private Boolean multiPlatform;

    /**
     * root 账号 root账号不受权限管控 (如果数据库中已经存在root账号 则以数据库中的为准)
     */
    private List<RootAccount> root = new ArrayList<>();
    /**
     * 平台配置
     */
    private List<MultiPlatformConfig> multiPlatformList = new ArrayList<>();

    public Boolean getMultiPlatform() {
        return multiPlatform;
    }

    public void setMultiPlatform(Boolean multiPlatform) {
        this.multiPlatform = multiPlatform;
    }

    public List<RootAccount> getRoot() {
        return root;
    }

    /**
     * 新增Root账号
     *
     * @param root
     */
    public void addRoot(RootAccount root) {
        if (!this.root.contains(root)) {
            this.root.add(root);
        }
    }

    public void setRoot(List<RootAccount> root) {
        this.root = root;
    }

    public List<MultiPlatformConfig> getMultiPlatformList() {
        return multiPlatformList;
    }

    public void setMultiPlatformList(List<MultiPlatformConfig> multiPlatformList) {
        this.multiPlatformList = multiPlatformList;
    }

    @Override
    public String toString() {
        return "LoginConfig{" +
                "multiPlatform=" + multiPlatform +
                ", root=" + root +
                ", multiPlatformList=" + multiPlatformList +
                "} " + super.toString();
    }
}
