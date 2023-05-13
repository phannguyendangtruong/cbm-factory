package com.amitgroup.models.verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;


public enum VerificationType {
    EMAIL(1, "EMAIL"),
    ;

    private final int type;
    private final String code;

    VerificationType(int status, String code) {
        this.type = status;
        this.code = code;
    }

    public static VerificationType from(Integer type) {
        if (type == null){
            return null;
        }

        for (VerificationType userType : VerificationType.values()) {
            if (userType.getType() == type) {
                return userType;
            }
        }

        return null;
    }

    public static VerificationType from(String code) {
        if (StringUtils.isEmpty(code)){
            return null;
        }

        for (VerificationType userType : VerificationType.values()) {
            if (userType.getCode().equals(code)) {
                return userType;
            }
        }

        return null;
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
