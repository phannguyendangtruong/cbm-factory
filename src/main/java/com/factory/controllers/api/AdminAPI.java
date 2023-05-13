package com.factory.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class AdminAPI {
    public static final String GET_ALL_USER = "/dashboard/user/list";
    public static final String SEARCH_USER = "/dashboard/user/search";
    public static final String GET_USER_BY_ID = "/dashboard/user/detail";
    public static final String CREATE_USER = "/dashboard/user/create";
    public static final String UPDATE_USER = "/dashboard/user/update";
    public static final String CHANGE_ACTIVE = "/dashboard/user/change-active";
    public static final String WORKER_LIST = "/worker/list";

}
