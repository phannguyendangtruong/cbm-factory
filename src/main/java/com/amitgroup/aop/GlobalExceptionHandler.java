package com.amitgroup.aop;


import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amitgroup.domains.NotificationDomain;
import com.amitgroup.models.ApiException;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.models.ERROR;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    NotificationDomain notificationDomain;

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleCustomizedException(ApiException e) {
        if (StringUtils.isEmpty(e.getMessageCode())){
            e.setMessageCode("UNKNOWN");
        }

        BaseResponse response = new BaseResponse(e);

        log.debug(response.toString());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse> handleConstraintViolationException(ConstraintViolationException e) {
        BaseResponse response = new BaseResponse(
                ERROR.INVALID_PARAM.getCode(),
                ERROR.INVALID_PARAM.getMessage(),
                "UNKNOWN"
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleGlobalException(Exception ex, @Nullable Object body, WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ExceptionUtils.getStackTrace(ex));
        log.error(stringBuilder.toString());
        notificationDomain.sendErrorEmail(request, body, ex.getMessage(), stringBuilder);

        BaseResponse response = new BaseResponse(
                ERROR.INTERNAL_SERVER.getCode(),
                ex.getLocalizedMessage(),
                ex.getMessage().hashCode() + ""
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}

