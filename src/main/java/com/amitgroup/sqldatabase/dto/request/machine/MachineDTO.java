package com.amitgroup.sqldatabase.dto.request.machine;

import lombok.Data;

@Data
public class MachineDTO {
    private Long id;
    private String name;
    private String description;
    private String code;
}
