package com.amitgroup.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class Notification {
    public static final String GET_ALL_NOTIFICATION = "/notification/get-all";
    public static final String GET_NOTIFICATION_BY_ID = "/notification/get-by-id";
    public static final String GET_NOTIFICATION_BY_NAME = "/notification/get-by-name";
    public static final String UPDATE_NOTIFICATION = "/notification/update";
    public static final String DELETE_NOTIFICATION = "/notification/delete";
    public static final String CREATE_NOTIFICATION = "/notification/create";

}
