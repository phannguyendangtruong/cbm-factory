package com.factory.sqldatabase.dto.response.maintenance;

import com.factory.sqldatabase.entities.MaintenancePerson;

import lombok.Data;

@Data
public class ListPerson {
    private Long userId;
    private String fullName;
    private Boolean isMainUser = false;

    public ListPerson(MaintenancePerson maintenancePerson) {
        this.userId = maintenancePerson.getUserId();
        this.fullName = maintenancePerson.getUser().getFullName();
        this.isMainUser = maintenancePerson.getIsMainPerson();
    }
}
