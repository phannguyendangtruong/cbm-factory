package com.amitgroup.services.machine;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.sqldatabase.dto.request.machine.MachineDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface MachineService {
    
    BaseResponse getAllMachines();
    BaseResponse getMachineById(Long id);
    BaseResponse createMachine(MachineDTO machineDTO);
    BaseResponse updateMachine(MachineDTO machineDTO);

}
