package com.amitgroup.sqldatabase.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;


public enum TokenType {
    ACCESS_TOKEN(1, "ACCESS_TOKEN"),
    REFRESH_TOKEN(2, "REFRESH_TOKEN"),
    RESET_PASSWORD(3, "RESET_PASSWORD");

    private final int type;
    private final String code;

    TokenType(int status, String code) {
        this.type = status;
        this.code = code;
    }

    public static TokenType from(Integer type) {
        if (type == null){
            return null;
        }

        for (TokenType tokenType : TokenType.values()) {
            if (tokenType.getType() == type) {
                return tokenType;
            }
        }

        return null;
    }

    public static TokenType from(String code) {
        if (StringUtils.isEmpty(code)){
            return null;
        }

        for (TokenType tokenType : TokenType.values()) {
            if (tokenType.getCode().equals(code)) {
                return tokenType;
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
