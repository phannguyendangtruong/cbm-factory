package com.amitgroup.models;

public enum ERROR {
    SUCCESS(200, "success"),
    INVALID_REQUEST(400, "invalid request"),
    INVALID_PARAM(400, "invalid param"),
    BLOCKED_USER(403, "transaction is blocked"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    UNAVAILABLE(503, "service unavailable"),
    INTERNAL_SERVER(500, "Internal Server Error"),
    IP_BLOCK(429 ,"this ip block" ),
    OTP_INVALID(400 ,"Otp invalid" ),
    EXCEED_RETRY_LIMIT(429, "exceed retry limit"),
    NOT_FOUND_RESOURCE(404, "not found resource")

    ;
    private int code;
    private String message;

    ERROR(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
