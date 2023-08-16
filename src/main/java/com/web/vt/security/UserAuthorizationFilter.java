package com.web.vt.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.web.vt.exceptions.InvalidTokenException;
import com.web.vt.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class UserAuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public UserAuthorizationFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(StringUtil.isEmpty(header) || !jwtUtil.isStartWithPrefix(header) ||!jwtUtil.isAccessToken(header)){
            filterChain.doFilter(request, response);
            return;
        }
        Optional<DecodedJWT> decodedJWT = jwtUtil.verifyAccessToken(header);
        if(decodedJWT.isEmpty()){
            throw new InvalidTokenException("INVALID ACCESS TOKEN");
        }
        String id = decodedJWT.get().getClaim("id").asString();
        UserDetails details = userDetailsService.loadUserByUsername(id);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.authenticated(details, null, details.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }


}
