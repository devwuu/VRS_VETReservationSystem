package com.web.vt.utils;

import com.web.vt.security.AdminPrincipal;
import com.web.vt.security.EmployeePrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private static final Authentication AUTHENTICATION = SecurityContextHolder.getContext().getAuthentication();

    public static EmployeePrincipal getEmployeePrincipal(){
        if(ObjectUtil.isEmpty(AUTHENTICATION)){
            throw new IllegalStateException("AUTHENTICATION IS EMPTY");
        }
        return (EmployeePrincipal) AUTHENTICATION.getPrincipal();
    }

    public static AdminPrincipal getAdminPrincipal(){
        if(ObjectUtil.isEmpty(AUTHENTICATION)){
            throw new IllegalStateException("AUTHENTICATION IS EMPTY");
        }
        return (AdminPrincipal) AUTHENTICATION.getPrincipal();
    }

    private SecurityUtil(){

    }

}
