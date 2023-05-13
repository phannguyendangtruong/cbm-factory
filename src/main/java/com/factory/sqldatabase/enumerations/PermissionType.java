package com.factory.sqldatabase.enumerations;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public enum PermissionType {
    FULL_PERMISSION(1, "FULL_PERMISSION"),
    ACCESS_DASHBOARD(2, "ACCESS_DASHBOARD"),
    USER_MANAGEMENT(3,"USER_MANAGEMENT"),
    MAINTENANCE_MANAGEMENT(4, "MAINTENANCE_MANAGEMENT"),
    VIEW_MAINTENANCE(5, "VIEW_MAINTENANCE"),
    EVALUATION_MANAGEMENT(6, "EVALUATE_MAINTENANCE"),
    ASSIGN_TO_MAINTENANCE(7, "ASSIGN_TO_MAINTENANCE"),
    CONFIRM_MAINTENANCE(8, "CONFIRM_MAINTENANCE"),
    ;

    private final int type;
    private final String code;

    PermissionType(int type, String code) {
        this.type = type;
        this.code = code;
    }

    public static PermissionType from(int type) {
        for (PermissionType _type : PermissionType.values()) {
            if (_type.getType() == type) {
                return _type;
            }
        }

        return null;
    }

    public static PermissionType from(String code) {
        for (PermissionType _type : PermissionType.values()) {
            if (_type.getCode().equals(code)) {
                return _type;
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

    public static List<String> listCode() {
        List<String> codes =  new ArrayList<>();

        for (PermissionType _type : PermissionType.values()) {
            codes.add(_type.getCode());
        }

        return codes;
    }

}
