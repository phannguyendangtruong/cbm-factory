package com.amitgroup.models.user;


import com.amitgroup.models.BaseDetailResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends BaseDetailResponse {

    @Schema(description = "email address", example = "user@mail.com")
    private String email;

    @Schema(description = "first name", example = "Bui")
    private String firstName;

    @Schema(description = "last name", example = "Thanh Phuong")
    private String lastName;

    @Schema(description = "user activation status", example = "true")
    private Boolean isActivated;

    @Schema(description = "email verification status", example = "true")
    private Boolean isVerifiedEmail;

    // public static UserResponse of(User user, String uid) {
    //     UserResponse dto = new UserResponse();
    //     dto.setId(user.getId());
    //     dto.setUid(uid);
    //     dto.setEmail(user.getEmail());
    //     dto.setFirstName(user.getFirstName());
    //     dto.setLastName(user.getLastName());
    //     dto.setIsActivated(BooleanUtils.safeUnboxing(user.getIsActivated()));
    //     dto.setIsVerifiedEmail(BooleanUtils.safeUnboxing(user.getIsVerifiedEmail()));
    //     return dto;
    // }
}
