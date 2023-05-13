package com.factory.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class MaintenanceAPI {

    public static final String GET_MAINTENANCE_BY_ID = "/maintenance/detail";
    public static final String UPDATE_MAINTENANCE = "/maintenance/update";
    public static final String CREATE_MAINTENANCE = "/maintenance/create";
    public static final String CHANGE_STATUS_MAINTENANCE = "/maintenance/change-status";
    public static final String DELETE_MAINTENANCE = "/maintenance/delete";
    public static final String GET_ALL_MAINTENANCE = "/maintenance/list";
    public static final String SEARCH_MAINTENANCE = "/maintenance/search";
    public static final String SORT_BY_CONTENT = "/maintenance/sort-by-content";
    public static final String GET_MAINTENANCE_BY_STATUS = "/maintenance/list/status";
    //person
    public static final String UPDATE_MAINTENANCE_PERSON = "/maintenance/person/reassign-person";
    public static final String CREATE_MAINTENANCE_PERSON = "/maintenance/person/assign-person";
    public static final String CONFIRM_WORK = "/worker/confirm";
    public static final String SET_DONE_WORK = "/worker/set-done";

    public static final String MAINTENANCE_STATUS = "/maintenance/status";
    public static final String MAINTENANCE_LEVEL = "/maintenance/level";

    //woker
    public static final String SEARCH_MAINTENANCE_BY_WORKER = "/maintenance/worker/search";


}
