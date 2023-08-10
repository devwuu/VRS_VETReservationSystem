package com.web.vt.security.admin;

import com.web.vt.domain.user.AdminService;
import com.web.vt.domain.user.AdminVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class AdminDetailService implements UserDetailsService {

    private final AdminService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminVO find = service.findById(username);
        return new AdminPrincipal(find);
    }
}
