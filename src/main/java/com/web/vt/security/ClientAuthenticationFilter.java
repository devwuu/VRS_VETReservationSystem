package com.web.vt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.vt.domain.employee.EmployeeVO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class ClientAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProviders jwtProviders;

    public ClientAuthenticationFilter(AuthenticationManager authenticationManager,
                                      JwtProviders jwtProviders) {
        this.authenticationManager = authenticationManager;
        this.jwtProviders = jwtProviders;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            EmployeeVO loginInfo = mapper.readValue(request.getInputStream(), EmployeeVO.class);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.id(), loginInfo.password());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        EmployeePrincipal principal = (EmployeePrincipal) authResult.getPrincipal();
        String token = jwtProviders.authenticate(principal);
        response.addHeader("Authorization", token);

    }
}
