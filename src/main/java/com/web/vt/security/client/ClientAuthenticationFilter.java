package com.web.vt.security.client;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.vt.exceptions.InvalidTokenException;
import com.web.vt.security.AuthenticationRequest;
import com.web.vt.security.AuthenticationResponse;
import com.web.vt.security.JwtService;
import com.web.vt.utils.JsonUtil;
import com.web.vt.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

@Slf4j
public class ClientAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmployeeDetailService clientDetailService;

    public ClientAuthenticationFilter(AuthenticationManager authenticationManager,
                                      JwtService jwtService,
                                      EmployeeDetailService employeeDetailService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.clientDetailService = employeeDetailService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        AuthenticationRequest authenticationRequest = JsonUtil.readValue(request, AuthenticationRequest.class);
        if(StringUtil.isEmpty(authenticationRequest.refreshToken())){
            UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(authenticationRequest.id(), authenticationRequest.password());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        }else {

            Optional<DecodedJWT> verifyToken = jwtService.verifyRefreshToken(authenticationRequest.refreshToken());
            DecodedJWT decodedJWT = verifyToken.orElseThrow(() -> new InvalidTokenException("INVALID TOKEN"));
            UserDetails userDetails = clientDetailService.loadUserByUsername(decodedJWT.getClaim("id").asString());
            UsernamePasswordAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
            return authenticated;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        EmployeePrincipal principal = (EmployeePrincipal) authResult.getPrincipal();
        // access token, refresh token 모두 재발급
        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse().accessToken(accessToken).refreshToken(refreshToken);
        JsonUtil.writeValue(response.getOutputStream(), authenticationResponse);
    }
}
