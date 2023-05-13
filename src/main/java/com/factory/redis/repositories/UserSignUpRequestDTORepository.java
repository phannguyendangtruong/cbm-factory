package com.factory.redis.repositories;

import org.springframework.data.repository.CrudRepository;

import com.factory.redis.entities.UserSignUpRequestDTO;

public interface UserSignUpRequestDTORepository extends CrudRepository<UserSignUpRequestDTO, String> {
}
