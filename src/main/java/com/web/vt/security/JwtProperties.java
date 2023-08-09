package com.web.vt.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ConfigurationProperties(prefix = "app.security.jwt")
@Getter @Setter
public class JwtProperties {

    private String secret;
    private int limit;
    private String issuer;
    private String prefix = "Bearer ";

    public Instant getExpiredTime(){
        return LocalDateTime.now().plusMinutes(limit).toInstant(ZoneOffset.UTC);
    }

    public Algorithm getSign(){
        return Algorithm.HMAC256(secret);
    }

}
