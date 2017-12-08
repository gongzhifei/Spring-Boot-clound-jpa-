package com.xxd.config.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 访问决策器
 * @author gongzhifei
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * decide 方法是判定是否拥有权限的决策方法，
     * @param authentication 自定义userDetails插入的GrantedAuthority权限集合
     * @param object 包含客户端发起的请求的requset信息，可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param configAttributes 数据库中读取出的权限集合
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if (null == configAttributes || configAttributes.size() < 0) {
            return;
        }
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String restUrl = getRequestPath(request);
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()){
            if(StringUtils.equals(restUrl,grantedAuthority.getAuthority())){
                return;
            }
        }
        throw new AccessDeniedException("Access is denied");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();

        if (request.getPathInfo() != null) {
            url += request.getPathInfo();
        }

        return url;
    }
}
