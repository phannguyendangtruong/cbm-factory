package com.amitgroup.models.auth;


import com.amitgroup.models.BaseDetailResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse extends BaseDetailResponse {

    @Schema(description = "email address", example = "user@mail.com")
    private String email;

    @Schema(description = "first name", example = "Bui")
    private String firstName;

    @Schema(description = "last name", example = "Thanh Phuong")
    private String lastName;

    @Schema(description = "email verification status", example = "true")
    private Boolean isVerifiedEmail;

    @Schema(description = "user type", example = "GENERAL_USER, ADMIN")
    private String type;

    // public static UserProfileResponse of(User user, String uid) {
    //     UserProfileResponse dto = new UserProfileResponse();
    //     dto.setId(user.getId());
    //     dto.setUid(uid);
    //     dto.setEmail(user.getEmail());
    //     dto.setFirstName(user.getFirstName());
    //     dto.setLastName(user.getLastName());
    //     dto.setType(UserType.from(user.getType()).getCode());
    //     dto.setIsVerifiedEmail(BooleanUtils.safeUnboxing(user.getIsVerifiedEmail()));
    //     return dto;
    // }
}
