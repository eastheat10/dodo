package com.nhnacademy.gateway.dto;

import com.nhnacademy.gateway.dto.response.user.UserResponse;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails {

    private String username;
    private String password;
    private String email;
    private String status;

    private List<? extends GrantedAuthority> authorities;

    public CustomUser(UserResponse userResponse) {
        this.username = userResponse.getUsername();
        this.password = userResponse.getPassword();
        this.email = userResponse.getEmail();
        this.status = userResponse.getUserStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
