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

public class ClientAuthorizationFilter extends OncePerRequestFilter {

    private final EmployeeDetailService employeeDetailService;

    public ClientAuthorizationFilter(EmployeeDetailService employeeDetailService) {
        this.employeeDetailService = employeeDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(StringUtil.isEmpty(header) || !StringUtil.startsWith(header, JwtProperties.PRE_FIX)){
            filterChain.doFilter(request, response);
            return;
        }
        String id = JWT.require(JwtProperties.SIGN)
                .build()
                .verify(StringUtil.remove(header, JwtProperties.PRE_FIX))
                .getClaim("id")
                .asString();
        EmployeePrincipal principal = (EmployeePrincipal) employeeDetailService.loadUserByUsername(id);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal.getUsername(), null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request, response);
    }
}
