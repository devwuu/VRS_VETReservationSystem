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
    private int accessTokenExpiredTime;
    private int refreshTokenExpiredTime;
    private String issuer;
    private String prefix = "Bearer ";
    private String refreshTokenSubject = "refresh";
    private String accessTokenSubject = "refresh";

    public Instant getAccessTokenExpiredAt(){
        return LocalDateTime.now().plusMinutes(accessTokenExpiredTime).toInstant(ZoneOffset.UTC);
    }

    public Instant getRefreshTokenExpiredAt(){
        return LocalDateTime.now().plusMinutes(accessTokenExpiredTime).toInstant(ZoneOffset.UTC);
    }

    public Algorithm getSign(){
        return Algorithm.HMAC256(secret);
    }

}
