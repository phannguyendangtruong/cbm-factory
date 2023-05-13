package com.amitgroup.redis.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;
import com.amitgroup.models.ShareConstants;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@RedisHash(ShareConstants.RedisHash.LOGIN_SESSION)
public class CachedTokenDTO {
    @Id
    private String token;

    @Indexed
    private long currentUserId;

    private String userName;

    private Integer userType;

    private Set<String> roles;

    private Integer tokenType;

    private String ipAddress;


    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
