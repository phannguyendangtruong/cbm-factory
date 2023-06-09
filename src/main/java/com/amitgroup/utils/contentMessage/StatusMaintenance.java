package com.amitgroup.utils.contentMessage;

public enum StatusMaintenance {
    NOT_ASIGN("Chưa giao việc"),
    PROCESSING("Đang bảo trì"),
    DONE("Đã bảo trì"),
    DONT_EVALUATE("Chưa đánh giá"),
    ASIGN("Chờ xác nhận"),
    EVALUATE("Đã đánh giá");

    private final String value;

    StatusMaintenance(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
