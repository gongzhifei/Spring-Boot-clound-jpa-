package com.xxd.config.security;

import com.xxd.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

/**
 * @author GFZ
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private AuthenticationSuccess authenticationSuccess;

    @Autowired
    private AuthenticationFailure authenticationFailure;

    @Autowired
    private MyAccessDenied accessDenied;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(usernamePasswordAuthentication(),UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authentication/require")
                .and()
                .authorizeRequests()
                .antMatchers("/authentication/require",securityProperties.getLoginPage()).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDenied).and().csrf().disable();
    }

    @Bean
    public UsernamePasswordAuthentication usernamePasswordAuthentication() throws Exception {
        UsernamePasswordAuthentication usernamePasswordAuthentication = new UsernamePasswordAuthentication();
        usernamePasswordAuthentication.setAuthenticationManager(authenticationManager());
        usernamePasswordAuthentication.setAuthenticationSuccessHandler(authenticationSuccess);
        usernamePasswordAuthentication.setAuthenticationFailureHandler(authenticationFailure);
        return usernamePasswordAuthentication;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
