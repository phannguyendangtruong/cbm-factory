package com.amitgroup.domains;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amitgroup.models.ApiException;
import com.amitgroup.models.ERROR;
import com.amitgroup.models.verification.VerificationType;
import com.amitgroup.redis.entities.VerificationRequestDTO;
import com.amitgroup.redis.repositories.VerificationRequestDTORepository;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.utils.TimeUtils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Component
public class VerificationDomain extends BaseDomain {

    @Value("${com.amit.sample.verification.default.session.extend.time-to-live:180000}") // 3 minutes:  3 * 60L
    long sessionExtendTimeToLive;

    @Autowired
    VerificationRequestDTORepository verificationRequestDTORepository;

    public VerificationRequestDTO createVerificationSession(User user, VerificationType verificationType, String otp, long sessionTimeToLive, long otpTimeToLive) {
        String sessionId = UUID.randomUUID().toString();
        Long otpExpiredUnixTime = TimeUtils.getCurrentUnixTimeAfterMilliSeconds(otpTimeToLive);
        VerificationRequestDTO verificationRequestDTO = new VerificationRequestDTO();
        verificationRequestDTO.setUserId(user.getId());
        verificationRequestDTO.setEmail(user.getEmail());
        // verificationRequestDTO.setVerificationType(verificationType);
        verificationRequestDTO.setSessionId(sessionId);
        verificationRequestDTO.setTimeToLive(sessionTimeToLive);
        verificationRequestDTO.setOtp(otp);
        verificationRequestDTO.setExpiredTimeOtp(otpExpiredUnixTime);
        verificationRequestDTORepository.save(verificationRequestDTO);
        return verificationRequestDTO;
    }

    public VerificationRequestDTO findVerificationSession(String sessionId) throws ApiException {
        Optional<VerificationRequestDTO> verificationRequestDTOOptional = verificationRequestDTORepository.findById(sessionId);
        if (verificationRequestDTOOptional.isEmpty()) {
            log.debug("not found session {}", sessionId);
            throw new ApiException(ERROR.INVALID_REQUEST, "session expired");
        }
        return verificationRequestDTOOptional.get();
    }

    public VerificationRequestDTO renewOtpVerificationSession(String sessionId, String otp) {
        VerificationRequestDTO verificationRequestDTO = findVerificationSession(sessionId);
        verificationRequestDTO.setOtp(otp);
        Long otpExpiredUnixTime = TimeUtils.getCurrentUnixTimeAfterMilliSeconds(verificationRequestDTO.getTimeToLive());
        verificationRequestDTO.setExpiredTimeOtp(otpExpiredUnixTime);
        verificationRequestDTORepository.save(verificationRequestDTO);
        return verificationRequestDTO;
    }

    public VerificationRequestDTO verifySessionByOTP(String sessionId, String otp) {
        VerificationRequestDTO verificationRequestDTO = findVerificationSession(sessionId);
        Long currentUnixTime = new Date().getTime();
        if (verificationRequestDTO.isVerify()){
            return verificationRequestDTO;
        }

        if (verificationRequestDTO.getExpiredTimeOtp() < currentUnixTime) {
            log.debug("otp expired: current time {} , expired time {}", currentUnixTime, verificationRequestDTO.getExpiredTimeOtp());
            incorrectOTPHandler(verificationRequestDTO);
            return null;
        }

        if (!verificationRequestDTO.getOtp().equalsIgnoreCase(otp)) {
            log.debug("otp incorrect {}", verificationRequestDTO.getOtp());
            incorrectOTPHandler(verificationRequestDTO);
            return null;
        }

        verificationRequestDTO.setVerify(true);
        verificationRequestDTO.setTimeToLive(sessionExtendTimeToLive);
        verificationRequestDTORepository.save(verificationRequestDTO);
        return verificationRequestDTO;
    }

    public void incorrectOTPHandler(VerificationRequestDTO verificationRequestDTO){
        if (!verificationRequestDTO.isVerify()){
            int incorrectCounter = verificationRequestDTO.getCounterOtpInvalid();
            incorrectCounter = incorrectCounter + 1;
            log.debug("incorrect counter {}/{}", incorrectCounter, verificationRequestDTO.getOtpInvalidMaxTime() + 1);
            if (incorrectCounter > verificationRequestDTO.getOtpInvalidMaxTime()){
                verificationRequestDTORepository.delete(verificationRequestDTO);
                log.debug("clear verification session");
            } else {
                verificationRequestDTORepository.save(verificationRequestDTO);
            }
            throw new ApiException(ERROR.INVALID_REQUEST, "please verify again");
        }
    }


}