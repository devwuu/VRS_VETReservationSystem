package com.web.vt.security;

import com.web.vt.domain.common.enums.UsageStatus;
import com.web.vt.domain.user.AdminVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AdminPrincipal implements UserDetails {

    private final AdminVO admin;

    public AdminPrincipal(AdminVO adminVO) {
        this.admin = adminVO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return admin.password();
    }

    @Override
    public String getUsername() {
        return admin.id();
    }

    @Override
    public boolean isAccountNonExpired() {
        return admin.status().equals(UsageStatus.USE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return admin.status().equals(UsageStatus.USE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return admin.status().equals(UsageStatus.USE);
    }

    @Override
    public boolean isEnabled() {
        return admin.status().equals(UsageStatus.USE);
    }
}
