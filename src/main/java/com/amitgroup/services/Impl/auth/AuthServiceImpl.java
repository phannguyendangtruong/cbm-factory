package com.amitgroup.services.Impl.auth;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import com.amitgroup.domains.TokenStore;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.auth.AuthService;
import com.amitgroup.services.email.MailService;
import com.amitgroup.sqldatabase.dto.request.auth.LoginReq;
import com.amitgroup.sqldatabase.dto.request.user.UserDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;
import com.amitgroup.sqldatabase.dto.response.user.UserReponseDTO;
import com.amitgroup.sqldatabase.dto.response.user.UserTokenDTO;
import com.amitgroup.sqldatabase.entities.LoginActivity;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.entities.UserToken;
import com.amitgroup.sqldatabase.repositories.LoginActivityRepository;
import com.amitgroup.sqldatabase.repositories.UserRepository;
import com.amitgroup.sqldatabase.repositories.UserTokenRepository;
import com.amitgroup.utils.HttpUtils;
import com.amitgroup.utils.RestUtils;
import com.amitgroup.utils.TimeUtils;
import com.amitgroup.utils.validate.LoginRequestValidate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private MailService emailService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    private LoginActivityRepository loginActivityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LoginRequestValidate loginRequestValidate;

    @Override
    public BaseResponse login(LoginReq loginRequest, HttpServletRequest request, Errors validate) {
        // validate
        log.info("Login request: {}", loginRequest);
        ResponseHandler resp = new ResponseHandler();
        BaseResponse<UserReponseDTO> baseResponse = new BaseResponse<>();
        loginRequestValidate.validate(loginRequest, validate);
        if (validate.hasErrors()) {
            List<String> errors = validate.getAllErrors().stream().map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());
            baseResponse = new BaseResponse<>(validate.getAllErrors().get(0).getDefaultMessage().toString());
            log.info("Login fail: {}", errors);
            return baseResponse;
        }
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if (user == null) {
            user = userRepository.findByUsername(loginRequest.getEmail());
            if (user == null) {
                log.info("User not found: {}" + loginRequest.getEmail());
                baseResponse = new BaseResponse<>("User not found");
                return baseResponse;
            }
        }
        if (!bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.info("Wrong password: {}" + loginRequest.getPassword());
            baseResponse = new BaseResponse<>("Wrong password");
            return baseResponse;
        }

        Long timeToLive = getTimeToLiveByRemember(loginRequest.getRemember());
        LoginActivity loginActivity = new LoginActivity();
        loginActivity.setUser(user);
        loginActivity.setLoginIp(HttpUtils.getRequestIP(request));
        loginActivity.setUserAgent(HttpUtils.getUserAgent(request));
        loginActivity.setExpiredTime(new Date(timeToLive));
        loginActivityRepository.save(loginActivity);

        UserReponseDTO userReponseDTO = modelMapper.map(user, UserReponseDTO.class);
        userReponseDTO.setToken(tokenStore.userLogin(user, RestUtils.getRootIPRequest(request)).getToken());
        resp.setData(userReponseDTO);

        log.info("Login success");
        log.info("Role Id = {}", user.getRole().getId());
        baseResponse = new BaseResponse<>(userReponseDTO);

        return baseResponse;
    }

    @Override
    public BaseResponse confirmPassword(String token, String password) {
        log.info("Reset password");
        BaseResponse response = new BaseResponse();
        UserToken userToken = userTokenRepository.findByToken(token);

        if (userToken.getExpiredTime().before(new Date())) {
            log.info("Token expired: {}" + token);
            response = new BaseResponse("Token expired");
            return response;
        }
        if (userToken == null) {
            log.info("Invalid token {}" + token);
            response = new BaseResponse("Invalid token");
            return response;
        }
        User user = userRepository.findById(userToken.getUser().getId()).get();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        userTokenRepository.delete(userToken);
        response.setMessage("Password reset successfully");
        log.info("Password reset successfully");
        return response;
    }

    @Transactional
    @Override
    public BaseResponse forgotPassword(UserDTO user) {
        log.info("Forgot password");
        BaseResponse resp = new BaseResponse();
        User userEntity = userRepository.findByEmail(user.getEmail());
        if (userEntity == null) {
            log.info("User not found {}" + user.getEmail());
            resp = new BaseResponse("Failled");
            resp.setMessage("User not found");
            return resp;
        }
        List<UserToken> userTokens = userTokenRepository.findByUserId(userEntity.getId());
        if (userTokens.size() > 0) {
            userTokenRepository.deleteByUserId(userEntity.getId());
        }
        String resetToken = UUID.randomUUID().toString();
        UserToken userToken = new UserToken();
        userToken.setToken(resetToken);
        userToken.setUser(userEntity);
        userToken.setExpiredTime(TimeUtils.safeAddSecond(new Date(), 1800));
        userTokenRepository.save(userToken);

        try {
            String domain = "http://localhost:3000/confirm?token=" + resetToken;
            ClassPathResource resource = new ClassPathResource("template/template.html");
            byte[] binaryData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String template = new String(binaryData, StandardCharsets.UTF_8).replace("{domain}", domain);

            emailService.sendEmail(userEntity.getEmail(), "Reset password", template);
        } catch (MessagingException | IOException e) {
            log.info("Send email fail {}" + e.getMessage());
            resp = new BaseResponse("Fail");
            e.printStackTrace();
        }
        resp.setMessage("Reset password link has been sent to your email");
        UserTokenDTO userTokenDTO = modelMapper.map(userToken, UserTokenDTO.class);
        resp.setData(userTokenDTO);
        log.info("Reset password link has been sent to your email");
        return resp;
    }

    private Long getTimeToLiveByRemember(Boolean remember) {
        Long timeToLive = new Date().getTime();
        if (remember == null) {
            timeToLive = timeToLive + (24 * 60 * 60 * 1000);
        } else if (remember) {
            timeToLive = timeToLive + (14 * 24 * 60 * 60 * 1000);
        } else {
            timeToLive = timeToLive + (24 * 60 * 60 * 1000);
        }

        return timeToLive;
    }

    @Override
    public BaseResponse logOut(String token, HttpServletRequest request) {
        log.info("Logout");
        BaseResponse resp = new BaseResponse();
        tokenStore.deleteToken(RestUtils.getTokenFromHeader(request));
        resp.setMessage("Logout success");
        return resp;
    }
}
