package com.xxd.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Security 配置
 * @author GFZ
 */
@ConfigurationProperties(prefix = "xxd.security")
public class SecurityProperties {

    private String loginPage;

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

}
