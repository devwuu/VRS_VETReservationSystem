package com.web.vt.security.client;

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

public class ClientAuthorizationFilter extends OncePerRequestFilter {

    private final EmployeeDetailService employeeDetailService;
    private final JwtService jwtService;

    public ClientAuthorizationFilter(EmployeeDetailService employeeDetailService,
                                     JwtService jwtService) {
        this.employeeDetailService = employeeDetailService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(StringUtil.isEmpty(header) || !jwtService.isStartWithPrefix(header)){
            filterChain.doFilter(request, response);
            return;
        }

        String id = jwtService.decodeToken(header).getClaim("id").asString();

        EmployeePrincipal principal = (EmployeePrincipal) employeeDetailService.loadUserByUsername(id);
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.authenticated(principal.getUsername(), null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }
}
