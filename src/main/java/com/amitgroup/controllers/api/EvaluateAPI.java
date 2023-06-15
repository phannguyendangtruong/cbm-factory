package com.amitgroup.controllers.api;

import org.springframework.stereotype.Component;

@Component
public class EvaluateAPI {
    
    public static final String GET_ALL_EVALUATE = "/evaluation/list";
    public static final String GET_EVALUATE_BY_MAINTENANCE = "/evaluation/detail";
    public static final String CREATE_EVALUATE = "/evaluation/create";
    public static final String UPDATE_EVALUATE = "/evaluation/update";
    public static final String DELETE_EVALUATE = "/evaluation/delete";
}
