package com.web.vt.security;

import com.auth0.jwt.JWT;
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

    public AdminAuthorizationFilter(AdminDetailService adminDetailService) {
        this.adminDetailService = adminDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        if(StringUtil.isEmpty(authorization) || !StringUtil.startsWith(authorization, JwtProperties.PRE_FIX)){
            filterChain.doFilter(request, response);
            return;
        }

        String id = JWT.require(JwtProperties.SIGN)
                .build()
                .verify(StringUtil.remove(authorization, JwtProperties.PRE_FIX))
                .getClaim("id")
                .asString();

        AdminPrincipal principal = (AdminPrincipal) adminDetailService.loadUserByUsername(id);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }


}
