package com.web.vt.security;

import com.auth0.jwt.JWT;
import com.web.vt.security.admin.AdminRefreshToken;
import com.web.vt.security.admin.AdminRefreshTokenRepository;
import com.web.vt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties properties;
    private final AdminRefreshTokenRepository repository;

    public String generateAccessToken(UserDetails userDetails) {

        String token = JWT.create()
                .withSubject(properties.getAccessTokenSubject())
                .withClaim("id", userDetails.getUsername())
                .withExpiresAt(properties.getAccessTokenExpiredAt())
                .sign(properties.getSign());

        return properties.getPrefix() + token;
    }

    @Transactional
    public String generateRefreshToken(UserDetails userDetails) {

        String token = JWT.create()
                .withSubject(properties.getRefreshTokenSubject())
                .withClaim("id", userDetails.getUsername())
                .withExpiresAt(properties.getRefreshTokenExpiredAt())
                .sign(properties.getSign());

        AdminRefreshToken refreshToken = new AdminRefreshToken()
                .id(userDetails.getUsername())
                .refreshToken(token)
                .expiration((long) properties.getRefreshTokenExpiredTime());

        repository.save(refreshToken);

        return token;
    }

    public String authorize(String header){

        String id = JWT.require(properties.getSign())
                .build()
                .verify(StringUtil.remove(header, properties.getPrefix()))
                .getClaim("id")
                .asString();

        return id;
    }

    public String removePrefix(String header){
        return StringUtil.remove(header, properties.getPrefix());
    }

    public Boolean isStartWithPrefix(String header){
        return StringUtil.startsWith(header, properties.getPrefix());
    }


}

