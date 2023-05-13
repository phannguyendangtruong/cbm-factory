package com.amitgroup.redis.repositories;

import org.springframework.data.repository.CrudRepository;
import com.amitgroup.redis.entities.UserSignUpRequestDTO;

public interface UserSignUpRequestDTORepository extends CrudRepository<UserSignUpRequestDTO, String> {
}
