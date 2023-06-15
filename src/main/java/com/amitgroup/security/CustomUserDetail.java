package com.amitgroup.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetail extends UsernamePasswordAuthenticationToken {

    public CustomUserDetail(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomUserDetail(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
