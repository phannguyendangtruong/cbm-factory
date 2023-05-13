package com.amitgroup.security;

import io.vertx.core.json.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.amitgroup.domains.TokenStore;
import com.amitgroup.models.ShareConstants;
import com.amitgroup.redis.entities.CachedTokenDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Optional;


public class Oauth2AuthorizationFilter
        extends BasicAuthenticationFilter {

    private final TokenStore tokenStore;


    public Oauth2AuthorizationFilter(AuthenticationManager authenticationManager,
                                     TokenStore tokenStore
    ) {
        super(authenticationManager);
        this.tokenStore = tokenStore;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = request.getHeader(ShareConstants.Header.TOKEN_HEADER);

            JsonObject rs = new JsonObject();
            rs.put("code", 99);
            rs.put("message", "Login required");
            
            if (StringUtils.isEmpty(token)) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(rs.toString());
                return;
            }

            token = token.replaceFirst("(?i)" + ShareConstants.Header.TOKEN_PREFIX, "");

            Optional<CachedTokenDTO> tokenOpt = tokenStore.getSessionFromToken(token);

            if (tokenOpt.isEmpty()) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(rs.toString());
                return;
            }

            CachedTokenDTO tokenDTO = tokenOpt.get();
            AMGUserDetail userDetail = new AMGUserDetail(tokenDTO);
            CustomUserDetail customUserDetail = new CustomUserDetail(userDetail , null , userDetail.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(customUserDetail);
            chain.doFilter(request, response);

        } catch (AccessDeniedException ex) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);

            JsonObject rs = new JsonObject();
            rs.put("code", 99);
            rs.put("msg", "Login required");
            response.getWriter().write(rs.toString());
            return;
        }

    }


}
