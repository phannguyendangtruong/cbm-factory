package com.amitgroup.sqldatabase.dto.request.maintenance;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaintenancePersonDTO {
    private Long idMaintenanceRequest;
    private Long mainUserId;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Long> personId;
}
