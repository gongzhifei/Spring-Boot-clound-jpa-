package com.xxd.config;

import com.xxd.properties.RestUrlProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author gongzhifei
 */
@Configuration
@EnableConfigurationProperties(RestUrlProperties.class)
public class RestTemplateConfig {


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
