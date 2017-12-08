package com.xxd.config.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xxd.dto.user.Menu;
import com.xxd.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author gongzhifei
 */
@Component
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private MenuRepository menuRepository;

    private List<ConfigAttribute> list = null;

    /**
     * 加载所有权限
     */
    public void loadResourceDefine() throws JsonProcessingException {
        list = new ArrayList<ConfigAttribute>();
        ConfigAttribute cfg;
        List<Menu> urls = menuRepository.findAllByUrlIsNotNull();
        for (Menu menu: urls) {
            cfg = new SecurityConfig(menu.getUrl());
            list.add(cfg);
        }
    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(list ==null) {
            try {
                loadResourceDefine();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (ConfigAttribute cfg : list){
            resUrl = cfg.getAttribute();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return list;
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
