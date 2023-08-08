package com.web.vt.configuration;

import com.web.vt.utils.ObjectUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {

        return () -> {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(ObjectUtil.isEmpty(authentication) || !authentication.isAuthenticated()){
                return Optional.empty();
            }

           UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return Optional.of(userDetails.getUsername());
        };

    }

}
