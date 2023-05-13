package com.amitgroup.services.area;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.sqldatabase.dto.request.area.AreaDTO;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface AreaService {
    
    BaseResponse getAllAreas();
    BaseResponse getAreaById(Long id);
    BaseResponse createArea(AreaDTO areaDTO);
    BaseResponse updateArea(AreaDTO areaDTO);
}
