package com.amitgroup.services.Impl.maintenance;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import com.amitgroup.sqldatabase.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import com.amitgroup.domains.TokenStore;
import com.amitgroup.models.BasePaginationResponse;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.maintenance.MaintenanceService;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;
import com.amitgroup.sqldatabase.dto.response.evaluate.EvaluateReponseDTO;
import com.amitgroup.sqldatabase.dto.response.maintenance.ListPerson;
import com.amitgroup.sqldatabase.dto.response.maintenance.MaintenanceReponseDTO;
import com.amitgroup.sqldatabase.dto.response.maintenance.MaintenanceReportDTO;
import com.amitgroup.sqldatabase.entities.Evaluate;
import com.amitgroup.sqldatabase.entities.MaintenancePerson;
import com.amitgroup.sqldatabase.entities.MaintenanceRequest;
import com.amitgroup.sqldatabase.repositories.EvaluateRepository;
import com.amitgroup.sqldatabase.repositories.MaintenancePersonRepository;
import com.amitgroup.sqldatabase.repositories.MaintenanceRepository;
import com.amitgroup.utils.RestUtils;
import com.amitgroup.utils.contentMessage.StatusMaintenance;
import com.amitgroup.utils.validate.MaintenanceRequestValidate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private MaintenanceRequestValidate maintenanceRequestValidate;
    @Autowired
    private MaintenancePersonRepository maintenancePersonRepository;
    @Autowired
    private EvaluateRepository evaluateRepository;

    @Override
    public BasePaginationResponse getAllMaintenance(Pageable pageable) {
        log.info("Get all maintenance request");
        Page<MaintenanceRequest> maintenanceRequests = maintenanceRepository
                .findAllByIsDeletedFalseOrderByCreatedAtDesc(pageable);
        List<MaintenanceReponseDTO> maintenanceReponseDTOs = new ArrayList<>();
        for (MaintenanceRequest maintenanceRequest : maintenanceRequests) {
            MaintenanceReponseDTO maintenanceReponseDTO = new MaintenanceReponseDTO(maintenanceRequest);
            maintenanceReponseDTOs.add(maintenanceReponseDTO);
        }
        BasePaginationResponse basePaginationResponse = new BasePaginationResponse(maintenanceReponseDTOs,
                pageable.getPageNumber(), pageable.getPageSize(), maintenanceRequests.getTotalElements());
        basePaginationResponse.setMessage("Get all maintenance success {}");

        BaseResponse responseHandler = new BaseResponse();
        responseHandler.setData(maintenanceReponseDTOs);
        responseHandler.setMessage("Get all maintenance success");
        return basePaginationResponse;
    }

    @Override
    public BaseResponse getOneMaintenance(Long id) {
        log.info("Get one maintenance");
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(id).get();
        MaintenanceReponseDTO maintenanceReponseDTO = new MaintenanceReponseDTO(maintenanceRequest);
        List<MaintenancePerson> maintenancePersons = maintenancePersonRepository.findByMaintenanceRequestId(id);
        List<ListPerson> listPersons = new ArrayList<>();
        for (MaintenancePerson maintenancePerson : maintenancePersons) {
            ListPerson listPerson = new ListPerson(maintenancePerson);
            listPersons.add(listPerson);
        }
        maintenanceReponseDTO.setListPerson(listPersons);
        Evaluate e = evaluateRepository.findByMaintenanceRequestIdAndIsDeletedFalse(id);
        if (e != null) {
            EvaluateReponseDTO evaluateReponseDTO = new EvaluateReponseDTO(e);
            maintenanceReponseDTO.setEvaluate(evaluateReponseDTO);
        }

        BaseResponse responseHandler = new BaseResponse();
        responseHandler.setData(maintenanceReponseDTO);
        responseHandler.setMessage("Get one maintenance success");
        return responseHandler;
    }

    @Override
    public BaseResponse createMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request,
            Errors errors) {
        log.info("Create maintenance");
        BaseResponse responseHandler = new BaseResponse();
        maintenanceRequestValidate.validate(maintenanceRequestDTO, errors);
        OffsetDateTime now = OffsetDateTime.now();

        if (errors.hasErrors()) {
            responseHandler = new BaseResponse(errors.getAllErrors().get(0).getDefaultMessage().toString());
            return responseHandler;
        }
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest();
        maintenanceRequest.MaintenanceRequest(maintenanceRequestDTO);
        Long id = userRepository
                .findByUsername(
                        tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getUserName())
                .getId();
        maintenanceRequest.setCreatedBy(id);
        maintenanceRequest.setCreatedAt(now);
        maintenanceRequest.setEndDate(maintenanceRequestDTO.getEndDate());
        maintenanceRequestDTO.setId(maintenanceRepository.save(maintenanceRequest).getId());
        responseHandler.setData(maintenanceRequestDTO);
        responseHandler.setMessage("Create maintenance success");

        log.info("Create maintenance success ");
        return responseHandler;
    }

    @Override
    public BaseResponse updateMaintenance(MaintenanceRequestDTO maintenanceRequestDTO, HttpServletRequest request,
            Errors errors) {
        log.info("Update maintenance");
        BaseResponse responseHandler = new BaseResponse();
        maintenanceRequestValidate.validate(maintenanceRequestDTO, errors);
        OffsetDateTime now = OffsetDateTime.now();

        if (errors.hasErrors()) {
            log.info("Update maintenance failed {}" + errors.getAllErrors().get(0).getDefaultMessage());
            responseHandler = new BaseResponse("Update maintenance failed");
            responseHandler.setData(errors.getAllErrors().get(0).getDefaultMessage());
            return responseHandler;
        }
        Long id = userRepository
                .findByUsername(
                        tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getUserName())
                .getId();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(maintenanceRequestDTO.getId()).get();
        maintenanceRequest.MaintenanceRequest(maintenanceRequestDTO);
        maintenanceRequest.setModifiedAt(now);
        maintenanceRequest.setModifiedBy(id);
        maintenanceRequest.setId(maintenanceRequestDTO.getId());
        maintenanceRequest.setEndDate(maintenanceRequestDTO.getEndDate());
        maintenanceRepository.save(maintenanceRequest);
        responseHandler.setData(maintenanceRequestDTO);
        responseHandler.setMessage("Update maintenance success");
        log.info("Update maintenance success ");
        return responseHandler;
    }

    @Override
    public BaseResponse deleteMaintenance(Long id) {
        log.info("Delete maintenance");
        BaseResponse responseHandler = new BaseResponse();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(id).get();
        maintenanceRequest.setIsDeleted(true);
        maintenanceRepository.save(maintenanceRequest);
        responseHandler.setMessage("Delete maintenance success");

        List<MaintenancePerson> maintenancePersons = maintenancePersonRepository.findByMaintenanceRequestId(id);
        for (MaintenancePerson maintenancePerson : maintenancePersons) {
            maintenancePersonRepository.delete(maintenancePerson);
        }
        log.info("Delete maintenance success");
        return responseHandler;
    }

    @Override
    public BaseResponse changeStatusMaintenance(Long id, String status) {
        log.info("Update status maintenance");
        BaseResponse responseHandler = new BaseResponse();
        MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(id).get();
        maintenanceRequest.setStatus(status);
        maintenanceRepository.save(maintenanceRequest);
        responseHandler.setMessage("Update status maintenance success");
        log.info("Update status maintenance success {}" + maintenanceRequest);
        return responseHandler;
    }

    @Override
    public BasePaginationResponse searchMaintenance(String search, Pageable pageable) {
        log.info("Search maintenance");
        Page<MaintenanceRequest> maintenanceRequests = maintenanceRepository
                .findByIgnoreCaseContentContainingAndIsDeletedFalse(search, pageable);
        Page<MaintenanceReponseDTO> maintenanceReponseDTOs = maintenanceRequests.map(maintenanceRequest -> {
            MaintenanceReponseDTO maintenanceReponseDTO = new MaintenanceReponseDTO(maintenanceRequest);
            return maintenanceReponseDTO;
        });
        BasePaginationResponse responseHandler = new BasePaginationResponse(maintenanceReponseDTOs.getContent(),
                pageable.getPageNumber(), pageable.getPageSize(), maintenanceReponseDTOs.getTotalElements());
        responseHandler.setMessage("Search maintenance success {}" + maintenanceReponseDTOs);
        return responseHandler;
    }

    @Override
    public BaseResponse sortByContent(Pageable pageable) {
        log.info("Sort by content maintenance");
        Page<MaintenanceRequest> maintenanceRequests = maintenanceRepository.findAllByOrderByContent(pageable);
        Page<MaintenanceReponseDTO> maintenanceReponseDTOs = maintenanceRequests.map(maintenanceRequest -> {
            MaintenanceReponseDTO maintenanceReponseDTO = new MaintenanceReponseDTO(maintenanceRequest);
            return maintenanceReponseDTO;
        });
        BaseResponse responseHandler = new BaseResponse();
        responseHandler.setData(maintenanceReponseDTOs);
        responseHandler.setMessage("Sort by content maintenance success");
        return responseHandler;
    }

    @Override
    public BaseResponse requiredStatistics() {
        log.info("Get required statistics");
        BaseResponse responseHandler = new BaseResponse();
        MaintenanceReportDTO maintenanceReportDTO = new MaintenanceReportDTO();
        maintenanceReportDTO.setTotalRequest(maintenanceRepository.countByIsDeletedFalse());
        maintenanceReportDTO.setTotalIsMaintained(
                maintenanceRepository.countByStatusAndIsDeletedFalse(StatusMaintenance.PROCESSING.getValue()));
        maintenanceReportDTO.setTotalMaintained(
                maintenanceRepository.countByStatusAndIsDeletedFalse(StatusMaintenance.DONE.getValue()));
        maintenanceReportDTO.setTotalNotAssigned(
                maintenanceRepository.countByStatusAndIsDeletedFalse(StatusMaintenance.NOT_ASIGN.getValue()));
        maintenanceReportDTO.setTotalNotYetRated(
                maintenanceRepository.countByStatusAndIsDeletedFalse(StatusMaintenance.DONT_EVALUATE.getValue()));
        maintenanceReportDTO.setTotalAssigned(
                maintenanceRepository.countByStatusAndIsDeletedFalse(StatusMaintenance.ASIGN.getValue()));
        maintenanceReportDTO.setTotalYetRated(
                maintenanceRepository.countByStatusAndIsDeletedFalse(StatusMaintenance.EVALUATE.getValue()));
        responseHandler.setData(maintenanceReportDTO);
        responseHandler.setMessage("Get required statistics success");
        return responseHandler;
    }

    @Override
    public BaseResponse maintenanceLevel() {
        BaseResponse responseHandler = new BaseResponse();
        List<String> list = new ArrayList<>();
        list.add("Cao");
        list.add("Trung bình");
        list.add("Thấp");
        responseHandler.setData(list);
        responseHandler.setMessage("Get maintenance level success");
        return responseHandler;
    }

    @Override
    public BaseResponse maintenanceStatus() {
        BaseResponse responseHandler = new BaseResponse();
        List<String> list = new ArrayList<>();
        list.add(StatusMaintenance.NOT_ASIGN.getValue());
        list.add(StatusMaintenance.PROCESSING.getValue());
        list.add(StatusMaintenance.DONE.getValue());
        list.add(StatusMaintenance.DONT_EVALUATE.getValue());
        list.add(StatusMaintenance.ASIGN.getValue());
        responseHandler.setData(list);
        responseHandler.setMessage("Get maintenance status success");
        return responseHandler;
    }

    @Override
    public BasePaginationResponse getListMaintenanceByStatus(Pageable pageable) {
        log.info("Get list maintenance by status");
        BasePaginationResponse responseHandler = new BasePaginationResponse();
        Page<MaintenanceRequest> maintenanceRequests = maintenanceRepository
                .findByStatusAndIsDeletedFalse(StatusMaintenance.NOT_ASIGN.getValue(), pageable);
        List<MaintenanceReponseDTO> maintenanceReponseDTOs = new ArrayList<>();
        for (MaintenanceRequest maintenanceRequest : maintenanceRequests) {
            MaintenanceReponseDTO maintenanceReponseDTO = new MaintenanceReponseDTO(maintenanceRequest);
            maintenanceReponseDTOs.add(maintenanceReponseDTO);
        }
        responseHandler.setData(maintenanceReponseDTOs);
        responseHandler.setMessage("Get list maintenance by status success");
        return responseHandler;
    }

    @Override
    public BasePaginationResponse searchMaintenanceByWorker(String search, Pageable pageable,
            HttpServletRequest request) {
        BasePaginationResponse responseHandler = new BasePaginationResponse();
        Long userId = userRepository
                .findByUsername(
                        tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get().getUserName())
                .getId();
        Page<MaintenancePerson> list = maintenancePersonRepository.findByContentAndUserId(search, userId, pageable);
        List<MaintenanceReponseDTO> maintenanceReponseDTOs = new ArrayList<>();
        for (MaintenancePerson m : list) {
            MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(m.getMaintenanceRequestId()).get();
            MaintenanceReponseDTO maintenanceReponseDTO = new MaintenanceReponseDTO(maintenanceRequest);
            maintenanceReponseDTOs.add(maintenanceReponseDTO);
        }
        responseHandler.setData(maintenanceReponseDTOs);
        return responseHandler;

    }

}
