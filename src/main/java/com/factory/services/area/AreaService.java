package com.factory.services.area;

import com.factory.models.BaseResponse;
import com.factory.sqldatabase.dto.request.area.AreaDTO;
import com.factory.sqldatabase.dto.response.ResponseHandler;

public interface AreaService {
    
    BaseResponse getAllAreas();
    BaseResponse getAreaById(Long id);
    BaseResponse createArea(AreaDTO areaDTO);
    BaseResponse updateArea(AreaDTO areaDTO);
}
