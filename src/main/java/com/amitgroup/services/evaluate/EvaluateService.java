package com.amitgroup.services.evaluate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;

import com.amitgroup.models.BasePaginationResponse;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.sqldatabase.dto.request.evaluate.EvaluateReqDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface EvaluateService {
    BaseResponse createEvaluate(EvaluateReqDTO evaluateReqDTO);
    BaseResponse updateEvaluate(EvaluateReqDTO evaluateReqDTO, Errors validate, HttpServletRequest request);
    BaseResponse deleteEvaluate(Long id);
    BaseResponse getEvaluate(Long id);
    BasePaginationResponse getAllEvaluate(Pageable pageable);
}
