package com.amitgroup.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Override
    public CustomUserDetail getAuthentication() {
        return (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public AMGUserDetail getUserDetail() {
        return (AMGUserDetail) this.getAuthentication().getPrincipal();
    }

    @Override
    public Long getUserId() {
        return this.getUserDetail().getUserId();
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        return request;
    }

    @Override
    public String getUsername() {
        if (getUserId() == null) {
            return null;
        }
        return getUserId().toString();
    }

}
