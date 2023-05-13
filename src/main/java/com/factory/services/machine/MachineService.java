package com.factory.services.machine;

import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.request.machine.MachineDTO;
import com.factory.sqldatabase.dto.response.ResponseHandler;

public interface MachineService {
    
    BaseResponse getAllMachines();
    BaseResponse getMachineById(Long id);
    BaseResponse createMachine(MachineDTO machineDTO);
    BaseResponse updateMachine(MachineDTO machineDTO);

}
