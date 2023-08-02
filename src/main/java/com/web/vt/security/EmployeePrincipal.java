package com.web.vt.security;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.employee.EmployeeVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class EmployeePrincipal implements UserDetails {

    private final EmployeeVO employee;

    public EmployeePrincipal(EmployeeVO employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) employee::role);
    }

    @Override
    public String getPassword() {
        return employee.password();
    }

    @Override
    public String getUsername() {
        return employee.id();
    }

    @Override
    public boolean isAccountNonExpired() {
        return employee.status().equals(UsageStatus.USE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return employee.status().equals(UsageStatus.USE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return employee.status().equals(UsageStatus.USE);
    }

    @Override
    public boolean isEnabled() {
        return employee.status().equals(UsageStatus.USE);
    }
}
