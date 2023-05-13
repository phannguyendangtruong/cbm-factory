package com.factory.models.user;


import com.factory.models.BasePaginationRequest;
import com.factory.sqldatabase.entities.User;
import com.factory.utils.SpecificationUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Search user (supported fields: id, createdDate, firstName, lastName)")
public class SearchUserRequest extends BasePaginationRequest<User> {
    @Schema(description = "email", example = "user@mail.com", required = false)
    private String email;

    @JsonIgnore
    private Set<String> sortFields = new HashSet<>(List.of(new String[]{"id", "createdDate", "firstName", "lastName"}));

    @JsonIgnore
    @Override
    public Specification<User> getSpecification(){
        Specification<User> specification = (entity, cq, cb) -> cb.equal(entity.get("isDeleted"), false);

        if (StringUtils.isNotEmpty(email)){
            specification = SpecificationUtils.addSpecification(specification, (entity, cq, cb) -> cb.equal(entity.get("email"), email));
        }

        return specification;
    }
}
