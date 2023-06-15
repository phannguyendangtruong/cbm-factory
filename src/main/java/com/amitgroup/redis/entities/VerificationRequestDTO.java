package com.amitgroup.redis.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import com.amitgroup.models.ShareConstants;
import com.amitgroup.models.verification.VerificationType;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@RedisHash(ShareConstants.RedisHash.VERIFICATION_SESSION)
public class VerificationRequestDTO {
    @Id
    private String sessionId;

    private String email;

    private Long userId;

    private String otp;

    private int otpInvalidMaxTime = 5;

    private int counterOtpInvalid=0;

    private Long expiredTimeOtp;

    private boolean isVerify = false;

    private VerificationType verificationType;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
