package com.web.vt.security;

import com.web.vt.domain.common.dto.EmployeeClinicDTO;
import com.web.vt.domain.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class EmployeeDetailService implements UserDetailsService {

    private final EmployeeService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmployeeClinicDTO find = service.findById(username);
        return new EmployeePrincipal(find);
    }
}
