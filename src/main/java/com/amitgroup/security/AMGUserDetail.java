package com.amitgroup.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.amitgroup.redis.entities.CachedTokenDTO;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AMGUserDetail implements UserDetails {
    public AMGUserDetail(CachedTokenDTO tokenDTO){
        this.currentUserId = tokenDTO.getCurrentUserId();
        this.userId = tokenDTO.getCurrentUserId();
        this.userType = tokenDTO.getUserType();
        this.roles = tokenDTO.getRoles();
    }

    private long currentUserId;

    private long userId;

    private Integer userType;

    private String hashedPinCode;

    private boolean isUsedGoogleOTP = false;

    private Set<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles != null) {
            List<GrantedAuthority> grantedAuthorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role))
                    .collect(Collectors.toList());
            return grantedAuthorities;
        }

        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
