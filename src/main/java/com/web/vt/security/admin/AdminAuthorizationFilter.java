package com.web.vt.security.admin;

import com.web.vt.security.JwtService;
import com.web.vt.utils.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AdminAuthorizationFilter extends OncePerRequestFilter {

    private final AdminDetailService adminDetailService;
    private final JwtService jwtService;

    public AdminAuthorizationFilter(AdminDetailService adminDetailService,
                                    JwtService jwtService) {
        this.adminDetailService = adminDetailService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if(StringUtil.isEmpty(authorization) || !jwtService.isStartWithPrefix(authorization)){
            filterChain.doFilter(request, response);
            return;
        }

        String id = jwtService.decodeToken(authorization).getClaim("id").asString();
        AdminPrincipal principal = (AdminPrincipal) adminDetailService.loadUserByUsername(id);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.authenticated(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }


}
