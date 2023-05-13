package com.factory.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class UserAPI {
    public static final String GET_ALL_USER = "/management/user/get-all";
    public static final String GET_USER_BY_ID = "/user/get-by-id";
    public static final String UPDATE_USER = "/user/update";
    public static final String DEACTIVE_USER = "/user/deactive";
    public static final String USER_PROFILE = "/user/profile";
    public static final String DELETE_USER = "/management/user/delete";
    public static final String CHANGE_PASSWORD = "/user/update/password";
    public static final String GET_LIST_WORK = "/user/work/list";
    public static final String DETAIL_WORK = "/user/work/detail";
    public static final String GET_LIST_WORKER = "/user/dashboard/worker";
    public static final String GET_EVALUATE_BY_WORKER = "/user/evaluate";
}
