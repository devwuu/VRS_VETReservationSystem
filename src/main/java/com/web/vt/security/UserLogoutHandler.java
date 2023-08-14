package com.web.vt.security;

import com.web.vt.utils.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class UserLogoutHandler implements LogoutHandler, LogoutSuccessHandler {
                                        // todo handler 와 sccuessHandler를 분리시키는게 나을까?

    private final JwtService jwtService;

    public UserLogoutHandler(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        jwtService.destroyRefreshToken(userDetails);
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        JsonUtil.writeValue(response.getOutputStream(), "successfully logout!!!");
    }
}
