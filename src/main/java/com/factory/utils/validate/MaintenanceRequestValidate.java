package com.factory.utils.validate;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.factory.sqldatabase.dto.request.maintenance.MaintenanceRequestDTO;

@Component
public class MaintenanceRequestValidate implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenanceRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenanceRequestDTO maintenanceRequestDTO = (MaintenanceRequestDTO) target;
        Date date = new Date();
        if (maintenanceRequestDTO.getContent().isEmpty()) {
            errors.rejectValue("content", "content.empty","Content is not empty");
        }
        if (maintenanceRequestDTO.getLevel().isEmpty()) {
            errors.rejectValue("level", "level.empty","Level is not empty");
        }
       
        if (maintenanceRequestDTO.getAreaId() == null) {
            errors.rejectValue("areaId", "areaId.empty","Area is not empty");
        }
        if (maintenanceRequestDTO.getMachineId() == null) {
            errors.rejectValue("machineId", "machineId.empty","Machine is not empty");
        }

        if (maintenanceRequestDTO.getEndDate() == null ) {
            errors.rejectValue("endDate", "endDate.empty","End date is not empty ");
        }
        if (maintenanceRequestDTO.getEndDate().before(date)) {
            errors.rejectValue("endDate", "endDate.empty","End date is not greater than current date");
        }
    }
    

}
