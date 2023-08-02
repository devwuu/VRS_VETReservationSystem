package com.web.vt.security;

import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import com.web.vt.domain.common.enums.UsageStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class EmployeePrincipal implements UserDetails {

    private final EmployeeClinicDTO employee;

    public EmployeePrincipal(EmployeeClinicDTO employee) {
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of((GrantedAuthority) employee::employeeRole);
    }

    @Override
    public String getPassword() {
        return employee.employeePassword();
    }

    @Override
    public String getUsername() {
        return Long.toString(employee.employeeId());
    }

    public String getLoginId(){
        return employee.employeeLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return employee.employeeStatus().equals(UsageStatus.USE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return employee.employeeStatus().equals(UsageStatus.USE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return employee.employeeStatus().equals(UsageStatus.USE);
    }

    @Override
    public boolean isEnabled() {
        return employee.employeeStatus().equals(UsageStatus.USE);
    }
}
