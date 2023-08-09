package com.web.vt.configuration;

import com.web.vt.security.JwtProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class AppConfiguration {

    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
