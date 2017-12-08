package com.xxd.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * RestUrl配置
 * @author gongzhifei
 */
@ConfigurationProperties(prefix = "xxd.rest.url")
public class RestUrlProperties {

    private String userInfo;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
