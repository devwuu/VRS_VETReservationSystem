package com.web.vt.security;

import com.auth0.jwt.JWT;
import com.web.vt.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class JwtProviders {
    private final JwtProperties properties;

    public String authenticate(UserDetails userDetails) {

        String token = JWT.create()
                .withSubject(properties.getIssuer())
                .withClaim("id", userDetails.getUsername())
                .withExpiresAt(properties.getExpiredTime())
                .sign(properties.getSign());

        return properties.getPrefix() + token;
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
