package com.web.vt.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.vt.exceptions.InvalidTokenException;
import com.web.vt.utils.JsonUtil;
import com.web.vt.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Optional;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public UserAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AuthenticationRequest authenticationRequest = JsonUtil.readValue(request, AuthenticationRequest.class);

        if(StringUtil.isEmpty(authenticationRequest.refreshToken())){
            UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.unauthenticated(authenticationRequest.id(), authenticationRequest.password());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            return authenticate;
        }else{
            Optional<DecodedJWT> verifyToken = jwtService.verifyRefreshToken(authenticationRequest.refreshToken());
            DecodedJWT decodedJWT = verifyToken.orElseThrow(() -> new InvalidTokenException("INVALID TOKEN"));
            UserDetails userDetails = userDetailsService.loadUserByUsername(decodedJWT.getClaim("id").asString());
            UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
            return authenticationToken;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetails principal = (UserDetails) authResult.getPrincipal();

        // access token과 refresh token 모두 재발급
        String accessToken = jwtService.generateAccessToken(principal);
        String refreshToken = jwtService.generateRefreshToken(principal);

        AuthenticationResponse token = new AuthenticationResponse().accessToken(accessToken).refreshToken(refreshToken);
        JsonUtil.writeValue(response.getOutputStream(), token);

    }

}
