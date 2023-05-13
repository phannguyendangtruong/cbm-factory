package com.factory.services.Impl.evaluate;

import java.time.OffsetDateTime;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.factory.domains.TokenStore;
import com.factory.models.BasePaginationResponse;
import com.factory.models.BaseResponse;
import com.factory.services.evaluate.EvaluateService;
import com.factory.sqldatabase.dto.request.evaluate.EvaluateReqDTO;
import com.factory.sqldatabase.dto.response.evaluate.EvaluateReponseDTO;
import com.factory.sqldatabase.entities.Area;
import com.factory.sqldatabase.entities.Evaluate;
import com.factory.sqldatabase.entities.MaintenanceRequest;
import com.factory.sqldatabase.repositories.AreaRepository;
import com.factory.sqldatabase.repositories.EvaluateRepository;
import com.factory.sqldatabase.repositories.MaintenanceRepository;
import com.factory.utils.RestUtils;
import com.factory.utils.contentMessage.StatusMaintenance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EvaluateServiceImpl implements EvaluateService{

    @Autowired
    private EvaluateRepository evaluateRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private MaintenanceRepository maintenanceRequestRepo;
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public BaseResponse createEvaluate(EvaluateReqDTO evaluateReqDTO) {
        log.info("Create evaluate");
        BaseResponse response = new BaseResponse();
        MaintenanceRequest maintenanceRequest = maintenanceRequestRepo.findById(evaluateReqDTO.getMaintenanceRequestId()).get();
        OffsetDateTime now = OffsetDateTime.now();
        if(maintenanceRequest.getIsEvaluated()) {
            log.info("Evaluate already exists {}"+ evaluateReqDTO);
            response = new BaseResponse("Evaluate already exists");
            return response;
        }
        Evaluate evaluate = modelMapper.map(evaluateReqDTO, Evaluate.class);
        evaluate.setCreatedAt(now);
        Area area = areaRepository.findById(maintenanceRequest.getAreaId()).get();
        evaluate.setArea(area);
        evaluate.setEndDate(maintenanceRequest.getEndDate());
        evaluateRepository.save(evaluate);

        maintenanceRequest.setIsEvaluated(true);
        maintenanceRequest.setStatus(StatusMaintenance.EVALUATE.getValue());
        maintenanceRequestRepo.save(maintenanceRequest);

        response.setData(evaluateReqDTO);
        log.info("Create evaluate success");
        return response;
    }

    @Override
    public BaseResponse updateEvaluate(EvaluateReqDTO evaluateReqDTO, Errors validate, HttpServletRequest request) {
        log.info("Update evaluate");
        Evaluate evaluate = evaluateRepository.findByMaintenanceRequestIdAndIsDeletedFalse(evaluateReqDTO.getMaintenanceRequestId());
        String token = RestUtils.getTokenFromHeader(request);
        Long userId = tokenStore.getSessionFromToken(token).get().getCurrentUserId();
        OffsetDateTime now = OffsetDateTime.now();

        evaluate.setContent(evaluateReqDTO.getContent());
        evaluate.setQuality(evaluateReqDTO.getQuality());
        evaluate.setModifiedAt(now);
        evaluate.setModifiedBy(userId);
        evaluateRepository.save(evaluate);

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepo.findById(evaluateReqDTO.getMaintenanceRequestId()).get();
        maintenanceRequest.setIsEvaluated(true);
        maintenanceRequestRepo.save(maintenanceRequest);

        BaseResponse response = new BaseResponse();
        response.setMessage("Update success");
        response.setData(evaluateReqDTO);
        log.info("Update evaluate success");
        return response;
    }

    @Override
    public BaseResponse deleteEvaluate(Long id) {
        log.info("Delete evaluate {}"+id);
        BaseResponse response = new BaseResponse();
        Evaluate evaluate = evaluateRepository.findById(id).get();
        evaluate.setIsDeleted(true);
        evaluateRepository.save(evaluate);

        MaintenanceRequest maintenanceRequest = maintenanceRequestRepo.findById(evaluate.getMaintenanceRequest().getId()).get();
        maintenanceRequest.setIsEvaluated(false);
        maintenanceRequestRepo.save(maintenanceRequest);

        response.setMessage("Delete success");
        log.info("Delete evaluate success");
        return response;
    }

    @Override
    public BaseResponse getEvaluate(Long id) {
        log.info("Get evaluate");
        BaseResponse response = new BaseResponse();
        Evaluate evaluate = evaluateRepository.findByMaintenanceRequestIdAndIsDeletedFalse(id);
        if(evaluate == null) {
            log.info("Evaluate not found {}"+ id);
            response = new BaseResponse("Evaluate not found");
            return response;
        }
        EvaluateReponseDTO evaluateReponseDTO =  new EvaluateReponseDTO(evaluate);
        evaluateReponseDTO.setMaintenanceId(id);
        response.setData(evaluateReponseDTO);
        log.info("Get evaluate success");
        return response;
    }

    @Override
    public BasePaginationResponse getAllEvaluate(Pageable pageable) {
        log.info("Get all evaluate");
        EvaluateReponseDTO evaluateReponseDTO = new EvaluateReponseDTO();
        Page<Evaluate> evaluates = evaluateRepository.findAllByIsDeletedFalse(pageable);
        Page<EvaluateReponseDTO> evaluateReponseDTOs = evaluates.map(evaluate -> {
            EvaluateReponseDTO evaluateReponseDTO2 = new EvaluateReponseDTO(evaluate);
            return evaluateReponseDTO2;
        });

        BasePaginationResponse response = new BasePaginationResponse(evaluateReponseDTOs.getContent(),pageable.getPageNumber(), pageable.getPageSize(), evaluates.getTotalElements());
        log.info("Get all evaluate success");
        return response;
    }
    
}
