package com.amitgroup.sqldatabase.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;


public enum UserType {
    MANAGE(1, "MANAGE"),
    SUPERVISOR(2, "SUPERVISOR"),
    WORKER(3, "GENERAL_USER"),
    ADMIN(4, "ADMIN"),
    ANONYMOUS(99, "ANONYMOUS"),
    ;

    private final int type;
    private final String code;

    UserType(int status, String code) {
        this.type = status;
        this.code = code;
    }

    public static UserType from(Integer type) {
        if (type == null){
            return ANONYMOUS;
        }

        for (UserType userType : UserType.values()) {
            if (userType.getType() == type) {
                return userType;
            }
        }

        return ANONYMOUS;
    }

    public static UserType from(String code) {
        if (StringUtils.isEmpty(code)){
            return ANONYMOUS;
        }

        for (UserType userType : UserType.values()) {
            if (userType.getCode().equals(code)) {
                return userType;
            }
        }

        return ANONYMOUS;
    }

    @JsonProperty("type")
    public int getType() {
        return type;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }
}
