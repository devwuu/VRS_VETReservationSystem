package com.web.vt.security;

import com.web.vt.exceptions.CommonException;
import com.web.vt.exceptions.InvalidTokenException;
import com.web.vt.exceptions.NotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class FilterExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (NotFoundException notFoundException){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, notFoundException.getMessage());
        }catch (InvalidTokenException invalidTokenException){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, invalidTokenException.getMessage());
        }catch (CommonException commonException){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, commonException.getMessage());
        }
    }

}
