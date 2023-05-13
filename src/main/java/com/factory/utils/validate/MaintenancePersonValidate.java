package com.factory.utils.validate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.factory.sqldatabase.dto.request.maintenance.MaintenancePersonDTO;

@Component
public class MaintenancePersonValidate implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return MaintenancePersonDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MaintenancePersonDTO maintenancePersonDTO = (MaintenancePersonDTO) target;
        if (maintenancePersonDTO.getIdMaintenanceRequest() == null) {
            errors.rejectValue("idMaintenanceRequest", "idMaintenanceRequest.empty","work id is not empty");
        }
        if (maintenancePersonDTO.getMainUserId() == null) {
            errors.rejectValue("mainUserId", "mainUserId.empty","user id is not empty");
        }
        if (maintenancePersonDTO.getPersonId().isEmpty()) {
            errors.rejectValue("personId", "personId.empty","id person is not empty");
        }
    }

}
