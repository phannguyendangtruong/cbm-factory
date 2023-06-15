package com.amitgroup.sqldatabase.dto.response.maintenance;

import com.amitgroup.sqldatabase.entities.MaintenancePerson;

import lombok.Data;

@Data
public class ListPerson {
    private Long userId;
    private String fullName;
    private Boolean isMainUser = false;
    private Boolean isConfirm;
    private Boolean isDone;

    public ListPerson(MaintenancePerson maintenancePerson) {
        this.userId = maintenancePerson.getUserId();
        this.fullName = maintenancePerson.getUser().getFullName();
        this.isMainUser = maintenancePerson.getIsMainPerson();
        this.isConfirm = maintenancePerson.getConfirm();
        this.isDone = maintenancePerson.getIsDone();
    }

    public ListPerson(MaintenancePerson maintenancePerson, Boolean isConfirm, Boolean isDone) {
        this.userId = maintenancePerson.getUserId();
        this.fullName = maintenancePerson.getUser().getFullName();
        this.isMainUser = maintenancePerson.getIsMainPerson();
        this.isConfirm = isConfirm;
        this.isDone = isDone;
    }

}
