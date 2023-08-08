package com.web.vt.security;

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
    private final JwtProviders jwtProviders;

    public ClientAuthorizationFilter(EmployeeDetailService employeeDetailService,
                                     JwtProviders jwtProviders) {
        this.employeeDetailService = employeeDetailService;
        this.jwtProviders = jwtProviders;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(StringUtil.isEmpty(header) || !jwtProviders.isStartWithPrefix(header)){
            filterChain.doFilter(request, response);
            return;
        }

        String id = jwtProviders.authorize(header);

        EmployeePrincipal principal = (EmployeePrincipal) employeeDetailService.loadUserByUsername(id);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal.getUsername(), null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }
}
