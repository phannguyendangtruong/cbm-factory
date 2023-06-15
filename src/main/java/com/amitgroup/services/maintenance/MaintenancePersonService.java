package com.amitgroup.services.maintenance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface MaintenancePersonService {
    BaseResponse createMaintenancePerson(MaintenancePersonDTO user, Errors validate);
    BaseResponse updateMaintenancePerson(MaintenancePersonDTO user, Errors validate);
    BaseResponse confirmWork(Long maintenanceId, HttpServletRequest request);
    BaseResponse setDoneWork(Long maintenanceId, HttpServletRequest request);
}
