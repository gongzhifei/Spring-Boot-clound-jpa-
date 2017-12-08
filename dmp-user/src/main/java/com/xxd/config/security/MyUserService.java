package com.xxd.config.security;

import com.xxd.dto.user.Menu;
import com.xxd.dto.user.Role;
import com.xxd.dto.user.User;
import com.xxd.dto.user.UserInfo;
import com.xxd.repository.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gongzhifei
 */
@Component
public class MyUserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
//
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleMenuReposirtory roleMenuReposirtory;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("平台不支持此用户，请联系管理员开通平台用户!");
        }

        List<Integer> roleIds = userRoleRepository.queryByRoleId(user.getId());
        List<Role> roles = roleRepository.findByIdIn(roleIds);
        List<Integer> menuIds = roleMenuReposirtory.queryByRoleId(roleIds);
        List<Menu> menus = menuRepository.findByIdIn(menuIds);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        List<Integer> typeIds = userTypeRepository.findByUserId(user.getId());
        for (Menu menu:menus){
            if(StringUtils.isNotBlank(menu.getUrl())){
                grantedAuthorities.add(new SimpleGrantedAuthority(menu.getUrl()));
            }
        }
        String password = passwordEncoder.encode("202cb962ac59075b964b07152d234b70");
        return new UserInfo(user.getId(),username,password,true,true,true,true,grantedAuthorities,roles,typeIds);
    }
}
