package com.web.vt.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class AdminAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AdminAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
        this.authenticationManager = authenticationManager;
    }

    // todo 로그인 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

//        ObjectMapper mapper = new ObjectMapper();
//        mapper.readValue(request.getInputStream())

        log.info("===================AdminAuthenticationFilter attemptAuthentication==============");


        return super.attemptAuthentication(request, response);
    }

    // todo 로그인 완료시 jwt token 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
