package com.web.vt.security;

import com.web.vt.exceptions.InvalidTokenException;
import com.web.vt.utils.JsonUtil;
import com.web.vt.utils.StringUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class UserLogoutHandler implements LogoutHandler, LogoutSuccessHandler {

    private final JwtUtil jwtUtil;

    public UserLogoutHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader("Authorization");
        if(StringUtil.isEmpty(header) || !jwtUtil.isStartWithPrefix(header)){
            throw new InvalidTokenException("INVALID TOKEN");
        }
        jwtUtil.destroyToken(header);
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        JsonUtil.writeValue(response.getOutputStream(), "successfully logout!!!");
    }
}
