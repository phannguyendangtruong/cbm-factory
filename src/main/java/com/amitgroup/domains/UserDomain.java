package com.amitgroup.domains;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amitgroup.models.ApiException;
import com.amitgroup.models.ERROR;
import com.amitgroup.security.AMGUserDetail;
import com.amitgroup.sqldatabase.entities.User;
import com.amitgroup.sqldatabase.enumerations.PermissionType;
import com.amitgroup.sqldatabase.enumerations.UserType;
import com.amitgroup.sqldatabase.repositories.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserDomain extends BaseDomain {
    @Autowired
    UserRepository userRepository;

    public User getValidUserFromSession(AMGUserDetail userDetail) {
        Optional<User> userOptional = userRepository.findById(userDetail.getUserId());
        if (userOptional.isEmpty()) {
            throw new ApiException(ERROR.INVALID_REQUEST, "user not found!!");
        }

        // validate data
        User user = userOptional.get();
        validateUserSession(user);
        return user;
    }

    public User getUserByIdForUpdate(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new ApiException(ERROR.INVALID_REQUEST, "user not found (1)!!");
        }

        // validate data
        User user = userOptional.get();
        // if (user.getIsDeleted() != null && user.getIsDeleted()){
        // throw new ApiException(ERROR.INVALID_REQUEST, "user not found (2)!!");
        // }

        return user;
    }

    public Set<String> getDefaultPermissionByUserType(UserType userType) {
        Set<String> roleCodes = new HashSet<>();
            if(userType == null){
                userType = UserType.WORKER;
            };
            switch (userType) {
                case MANAGE:
                    roleCodes.add(PermissionType.ACCESS_DASHBOARD.getCode());
                    roleCodes.add(PermissionType.VIEW_MAINTENANCE.getCode());
                    roleCodes.add(PermissionType.ASSIGN_TO_MAINTENANCE.getCode());
                    roleCodes.add(PermissionType.MAINTENANCE_MANAGEMENT.getCode());
                    break;
                case SUPERVISOR:
                    roleCodes.add(PermissionType.MAINTENANCE_MANAGEMENT.getCode());
                    roleCodes.add(PermissionType.EVALUATION_MANAGEMENT.getCode());
                    break;
                case ADMIN:
                    roleCodes.add(PermissionType.FULL_PERMISSION.getCode());
                    break;

                case WORKER:
                    roleCodes.add(PermissionType.VIEW_MAINTENANCE.getCode());
                    roleCodes.add(PermissionType.CONFIRM_MAINTENANCE.getCode());
                    break;
                default:
                    break;
       
            }
        return roleCodes;
    }

    public void validateUserSession(User user) {
        // if (!BooleanUtils.safeUnboxing(user.getIsActivated())) {
        // throw new ApiException(ERROR.INVALID_REQUEST, "user not available (1)!!");
        // }

        // if (user.getIsDeleted() != null && user.getIsDeleted()) {
        // throw new ApiException(ERROR.INVALID_REQUEST, "user not available (2)!!");
        // }
    }

    public void validatePasswordRule(String password) {
        if (StringUtils.isBlank(password)
                || password.length() > 255
                || password.length() < 6) {
            throw new ApiException(ERROR.INVALID_PARAM, "password invalid");
        }
    }

    public void validateRegistrationEmail(String email) {
        // Optional<User> userOptional = userRepository.findByLogin(email);
        // if (userOptional.isPresent()) {
        // throw new ApiException(ERROR.INVALID_REQUEST, "this email has been
        // registered, please try another email");
        // }
    }

    public User getUserByEmail(String email) {
        // Optional<User> userOptional = userRepository.findByEmail(email);
        // if (userOptional.isEmpty()) {
        // throw new ApiException(ERROR.INVALID_REQUEST, "user not found (1)!!");
        // }

        // // validate data
        // User user = userOptional.get();
        // if (user.getIsDeleted() != null && user.getIsDeleted()) {
        // throw new ApiException(ERROR.INVALID_REQUEST, "user not found (2)!!");
        // }

        // return user;
        return null;
    }
}
