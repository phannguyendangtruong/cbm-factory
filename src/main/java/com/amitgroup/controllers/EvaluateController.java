package com.amitgroup.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.amitgroup.controllers.api.*;
import com.amitgroup.models.BasePaginationResponse;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.evaluate.EvaluateService;
import com.amitgroup.sqldatabase.dto.request.PageDTO;
import com.amitgroup.sqldatabase.dto.request.evaluate.EvaluateReqDTO;


@RestController
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequestMapping("/v1")
public class EvaluateController {

    @Autowired
    private EvaluateService evaluateService;

    @PostMapping(EvaluateAPI.GET_ALL_EVALUATE)
    public BasePaginationResponse getAllEvaluate(@RequestBody PageDTO pageDTO) {
        Pageable pageable = PageRequest.of(pageDTO.getPageIndex(), pageDTO.getPageSize());
        return evaluateService.getAllEvaluate(pageable);
    }

    @GetMapping(EvaluateAPI.GET_EVALUATE_BY_MAINTENANCE)
    public BaseResponse getEvaluate(@RequestParam Long maintenanceId) {
        return evaluateService.getEvaluate(maintenanceId);
    }

    @PostMapping(EvaluateAPI.CREATE_EVALUATE)
    public BaseResponse createEvaluate(@RequestBody EvaluateReqDTO evaluateReqDTO) {
        return evaluateService.createEvaluate(evaluateReqDTO);
    }

    @PostMapping(EvaluateAPI.UPDATE_EVALUATE)
    public BaseResponse updateEvaluate(@RequestBody EvaluateReqDTO evaluateReqDTO, Errors validate, HttpServletRequest request) {
        return evaluateService.updateEvaluate(evaluateReqDTO, validate, request);
    }

    @PostMapping(EvaluateAPI.DELETE_EVALUATE)
    public BaseResponse deleteEvaluate(@RequestBody EvaluateReqDTO evaluate) {
        return evaluateService.deleteEvaluate(evaluate.getId());
    }
}
