package com.web.vt.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
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
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (NotFoundException notFoundException){
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, notFoundException.getMessage());
            notFoundException.printStackTrace();
        }catch (InvalidTokenException | TokenExpiredException | SignatureVerificationException | JWTDecodeException invalidTokenException){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "INVALID TOKEN");
            invalidTokenException.printStackTrace();
        } catch (CommonException commonException){
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, commonException.getMessage());
            commonException.printStackTrace();
        }
    }

}
