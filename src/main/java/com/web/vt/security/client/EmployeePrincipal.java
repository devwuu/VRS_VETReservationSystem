package com.web.vt.security.client;

import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import com.web.vt.domain.common.enums.UsageStatus;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
public class EmployeePrincipal implements UserDetails {

    private final EmployeeClinicDTO employee;

    public EmployeePrincipal(EmployeeClinicDTO employee) {
        this.employee = employee;
    }

    public Long getClinicId(){
        return employee.clinicId();
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
        return employee.employeeId();
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
