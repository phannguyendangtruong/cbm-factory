package com.amitgroup.redis.entities;

import com.amitgroup.models.ShareConstants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@Setter
@RedisHash(ShareConstants.RedisHash.SIGN_UP_SESSION)
public class UserSignUpRequestDTO {
    @Id
    private String sessionId;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String otp;

    private int counterOtpInvalid=0;

    private Long expiredTimeOtp;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
