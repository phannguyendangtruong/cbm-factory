package com.amitgroup.services.role;

import org.springframework.stereotype.Service;

import com.amitgroup.models.BaseResponse;
import com.amitgroup.sqldatabase.dto.response.ResponseHandler;

public interface RoleService {
    BaseResponse getAllRole();
}
