package com.amitgroup.domains;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amitgroup.redis.entities.CachedTokenDTO;
import com.amitgroup.redis.repositories.CachedTokenRepository;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.enumerations.TokenType;
import com.amitgroup.sqldatabase.enumerations.UserType;
import com.amitgroup.sqldatabase.repositories.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
public class TokenStore extends BaseDomain {
    @Autowired
    CachedTokenRepository cachedTokenRepository;

    @Autowired
    UserDomain userDomain;

    @Value("${token.time-to-live}")
    long defaultTokenTimeToLive;

    @Value("${token.time-to-live}")
    long accessTokenTimeToLive;

    @Value("${refresh-token.time-to-live}")
    long refreshTokenTimeToLive;

    @Value("${reset-password-token.time-to-live}")
    long resetPasswordTokenTimeToLive;

    public CachedTokenDTO userLogin(User user, String ipAddress){
        TokenType tokenType = TokenType.ACCESS_TOKEN;
        String accessToken = UUID.randomUUID().toString();
        CachedTokenDTO cachedTokenDTO = new CachedTokenDTO();
        cachedTokenDTO.setToken(accessToken);
        cachedTokenDTO.setCurrentUserId(user.getId());
        cachedTokenDTO.setUserName(user.getUsername());
        cachedTokenDTO.setUserType(Integer.valueOf(user.getRole().getId()+""));
        cachedTokenDTO.setTokenType(tokenType.getType());
        cachedTokenDTO.setIpAddress(ipAddress);
        this.setTimeToLive(cachedTokenDTO, tokenType);
        UserType userType;
     
        switch (user.getRole().getId().intValue()) {
            case 1:
                userType = UserType.MANAGE;
                break;
            case 2:
                userType = UserType.SUPERVISOR;
                break;
            case 3:
                userType = UserType.WORKER;
                break;
            default:
                userType = UserType.ADMIN;
                break;
        }
        this.setDefaultPermissions(cachedTokenDTO, userType);
        return cachedTokenRepository.save(cachedTokenDTO);
    }

    public Optional<CachedTokenDTO> getSessionFromToken(String token){
        return cachedTokenRepository.findById(token);
    }

    public void deleteToken(String token){
        Optional<CachedTokenDTO> optionalTokenDTO = getSessionFromToken(token);
        if (optionalTokenDTO.isPresent()){
            cachedTokenRepository.deleteById(token);
        }
    }

    public void cleanUserLoginSession(Long userId) {
        List<CachedTokenDTO> tokenDTOS = cachedTokenRepository.findByCurrentUserId(userId);
        if (!tokenDTOS.isEmpty()){
            cachedTokenRepository.deleteAll(tokenDTOS);
        }
    }

    protected void setDefaultPermissions(CachedTokenDTO cachedTokenDTO, UserType userType){
        if (userType == null){
            userType = UserType.WORKER;
        }
        Set<String> permissionCodes = userDomain.getDefaultPermissionByUserType(userType);
        cachedTokenDTO.setRoles(permissionCodes);
    }

    protected void setTimeToLive(CachedTokenDTO cachedTokenDTO, TokenType tokenType){
        switch (tokenType){
            case ACCESS_TOKEN:
                cachedTokenDTO.setTimeToLive(accessTokenTimeToLive);
                break;
            case REFRESH_TOKEN:
                cachedTokenDTO.setTimeToLive(refreshTokenTimeToLive);
                break;
            case RESET_PASSWORD:
                cachedTokenDTO.setTimeToLive(resetPasswordTokenTimeToLive);
                break;
            default:
                cachedTokenDTO.setTimeToLive(defaultTokenTimeToLive);
                break;
        }
    }
}
