package com.amitgroup.models;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ApiException  extends RuntimeException{
    private int errorCode;
    private String errorMsg;
    private HttpStatus httpStatus;
    private String messageCode;

    public ApiException(ERROR exception) {
        super(exception.getMessage());
        this.errorCode = exception.getCode();
        this.errorMsg = exception.getMessage();
        this.httpStatus = HttpStatus.valueOf(exception.getCode());
    }

    public ApiException(ERROR exception, HttpStatus status) {
        super(exception.getMessage());
        this.errorCode = exception.getCode();
        this.httpStatus = status;
    }

    public ApiException(int code, String errorMsg) {
        super(errorMsg);
        this.errorCode = code;
        this.httpStatus = HttpStatus.valueOf(code);
    }

    public ApiException(ERROR exception, String errorMsg) {
        super(errorMsg);
        this.errorCode = exception.getCode();
        this.errorMsg = errorMsg;
        this.httpStatus = HttpStatus.valueOf(exception.getCode());
    }

    public ApiException(ERROR exception, String errorMsg, String messageCode) {
        super(errorMsg);
        this.errorCode = exception.getCode();
        this.errorMsg = errorMsg;
        this.httpStatus = HttpStatus.valueOf(exception.getCode());
        this.messageCode = messageCode;
    }

    public ApiException(IMessageCode iMessageCode) {
        super();
        this.errorMsg = iMessageCode.getMessage();
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.messageCode = iMessageCode.getCode();
    }

    public ApiException(int code, String errorMsg, HttpStatus httpStatus) {
        super(errorMsg);
        this.errorCode = code;
        this.httpStatus = httpStatus;
    }

}
