package com.amitgroup.services.Impl.maintenance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import com.amitgroup.utils.RestUtils;
import com.amitgroup.utils.contentMessage.StatusMaintenance;
import com.amitgroup.utils.validate.MaintenancePersonValidate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import com.amitgroup.domains.TokenStore;
import com.amitgroup.models.BaseResponse;
import com.amitgroup.services.maintenance.MaintenancePersonService;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.amitgroup.sqldatabase.entities.Evaluate;
import com.amitgroup.sqldatabase.entities.MaintenancePerson;
import com.amitgroup.sqldatabase.entities.MaintenanceRequest;
import com.amitgroup.sqldatabase.repositories.EvaluateRepository;
import com.amitgroup.sqldatabase.repositories.MaintenancePersonRepository;
import com.amitgroup.sqldatabase.repositories.MaintenanceRepository;

@Slf4j
@Service
public class MaintenancePersonServiceImpl implements MaintenancePersonService {

    @Autowired
    private MaintenancePersonRepository maintenancePersonRepository;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private MaintenancePersonValidate maintenancePersonValidate;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private EvaluateRepository evaluateRepository;

    @Override
    public BaseResponse createMaintenancePerson(MaintenancePersonDTO user, Errors validate) {
        log.info("create Maintenance Person");
        try {
            BaseResponse responseHandler = new BaseResponse();
            maintenancePersonValidate.validate(user, validate);
            if (validate.hasErrors()) {
                responseHandler.setMessage("Add maintenance person failed");
                responseHandler.setData(validate.getAllErrors().get(0).getDefaultMessage());
                log.info("Add maintenance person failed {}" + validate.getAllErrors().get(0).getDefaultMessage());
                return responseHandler;
            }
            MaintenancePerson maintenancePerson = new MaintenancePerson();
            MaintenancePerson maintenance = maintenancePersonRepository
                    .findByMaintenanceRequestIdAndUserId(user.getIdMaintenanceRequest(), user.getMainUserId());
            if (maintenance == null) {
                maintenancePerson.setUserId(user.getMainUserId());
                maintenancePerson.setIsMainPerson(true);
                maintenancePerson.setMaintenanceRequestId(user.getIdMaintenanceRequest());
                maintenancePersonRepository.save(maintenancePerson);

            }

            for (int i = 0; i < user.getPersonId().size(); i++) {
                maintenancePerson = new MaintenancePerson();
                maintenancePerson.setMaintenanceRequestId(user.getIdMaintenanceRequest());
                maintenancePerson.setUserId(user.getPersonId().get(i));
                MaintenancePerson maintenancePerson1 = maintenancePersonRepository
                        .findByMaintenanceRequestIdAndUserId(user.getIdMaintenanceRequest(), user.getPersonId().get(i));
                if (maintenancePerson1 != null) {
                    continue;
                }
                maintenancePersonRepository.save(maintenancePerson);
            }
            MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(user.getIdMaintenanceRequest())
                    .get();
            maintenanceRequest.setStatus(StatusMaintenance.ASIGN.getValue());
            maintenanceRepository.save(maintenanceRequest);

            responseHandler.setMessage("Create maintenance person success");
            responseHandler.setData(maintenancePerson);
            log.info("Create maintenance person success {}" + maintenancePerson);
            return responseHandler;
        } catch (Exception e) {
            log.info("Create maintenance person failed");
            return new BaseResponse(e.getMessage());
        }
    }

    @Transactional
    @Override
    public BaseResponse updateMaintenancePerson(MaintenancePersonDTO user, Errors validate) {
        log.info("updateMaintenancePerson {}" + user);
        try {
            BaseResponse responseHandler = new BaseResponse();
            MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(user.getIdMaintenanceRequest())
                    .get();
            maintenancePersonValidate.validate(user, validate);
            if (validate.hasErrors()) {
                log.info("Update maintenance person failed {}" + validate.getAllErrors().get(0).getDefaultMessage());
                responseHandler = new BaseResponse("Update maintenance person failed");
                responseHandler.setData(validate.getAllErrors().get(0).getDefaultMessage());
                return responseHandler;
            }

            if (maintenanceRequest != null) {
                maintenancePersonRepository.deleteByMaintenanceRequestId(user.getIdMaintenanceRequest());
                responseHandler.setData(createMaintenancePerson(user, validate).getData());
                responseHandler.setMessage("Update maintenance person success");
                log.info("Update maintenance person success {}" + responseHandler);
                return responseHandler;
            }
            responseHandler = new BaseResponse("Can't find maintenance request");
            return responseHandler;
        } catch (Exception e) {
            log.info("updateMaintenancePerson failed");
            return new BaseResponse(e.getMessage());
        }

    }

    @Override
    public BaseResponse confirmWork(Long maintenanceId, HttpServletRequest request) {
        log.info("confirmWork {}" + maintenanceId);
        try {
            List<MaintenancePerson> list = maintenancePersonRepository
                    .findByMaintenanceRequestId(maintenanceId);
            BaseResponse responseHandler = new BaseResponse();
            Long userId = tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get()
                    .getCurrentUserId();
            MaintenancePerson maintenancePerson = maintenancePersonRepository
                    .findByMaintenanceRequestIdAndUserId(maintenanceId, userId);
            maintenancePerson.setConfirm(true);
            maintenancePersonRepository.save(maintenancePerson);
            Boolean check = true;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getConfirm() == false) {
                    check = false;
                    break;
                }
            }
            if (check) {
                MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(maintenanceId).get();
                maintenanceRequest.setStatus(StatusMaintenance.PROCESSING.getValue());
                ;
                maintenanceRepository.save(maintenanceRequest);
            }

            log.info("Confirm work success {}" + responseHandler);
            return responseHandler;
        } catch (Exception e) {
            log.info("confirmWork failed");
            return new BaseResponse(e.getMessage());
        }
    }

    @Override
    public BaseResponse setDoneWork(Long maintenanceId, HttpServletRequest request) {
        log.info("changeDoneWork {}" + maintenanceId);
        try {
            BaseResponse responseHandler = new BaseResponse();
            Long userId = tokenStore.getSessionFromToken(RestUtils.getTokenFromHeader(request)).get()
                    .getCurrentUserId();
            MaintenancePerson maintenancePerson = maintenancePersonRepository
                    .findByMaintenanceRequestIdAndUserId(maintenanceId, userId);
            maintenancePerson.setIsDone(true);
            maintenancePersonRepository.save(maintenancePerson);
            List<MaintenancePerson> list = maintenancePersonRepository
                    .findByMaintenanceRequestId(maintenanceId);
            Boolean check = true;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getIsDone() == false) {
                    check = false;
                    break;
                }
            }
            MaintenanceRequest maintenanceRequest = maintenanceRepository.findById(maintenanceId).get();
            if (check) {
                Evaluate evaluate = evaluateRepository.findByMaintenanceRequestIdAndIsDeletedFalse(maintenanceId);
                if (evaluate == null) {
                    maintenanceRequest.setStatus(StatusMaintenance.DONT_EVALUATE.getValue());
                    maintenanceRepository.save(maintenanceRequest);
                } else {
                    maintenanceRequest.setStatus(StatusMaintenance.DONE.getValue());
                    maintenanceRepository.save(maintenanceRequest);
                }

            }

            log.info("Change done work success {}" + responseHandler);
            return responseHandler;
        } catch (Exception e) {
            log.info("changeDoneWork failed");
            return new BaseResponse(e.getMessage());
        }
    }

}
