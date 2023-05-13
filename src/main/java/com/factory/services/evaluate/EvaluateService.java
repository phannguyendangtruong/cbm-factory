package com.factory.services.evaluate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;

import com.factory.models.BasePaginationResponse;
import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.request.evaluate.EvaluateReqDTO;
import com.factory.sqldatabase.dto.response.ResponseHandler;

public interface EvaluateService {
    BaseResponse createEvaluate(EvaluateReqDTO evaluateReqDTO);
    BaseResponse updateEvaluate(EvaluateReqDTO evaluateReqDTO, Errors validate, HttpServletRequest request);
    BaseResponse deleteEvaluate(Long id);
    BaseResponse getEvaluate(Long id);
    BasePaginationResponse getAllEvaluate(Pageable pageable);
}
