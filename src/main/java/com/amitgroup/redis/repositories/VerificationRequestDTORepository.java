package com.amitgroup.redis.repositories;

import org.springframework.data.repository.CrudRepository;

import com.amitgroup.redis.entities.VerificationRequestDTO;

public interface VerificationRequestDTORepository extends CrudRepository<VerificationRequestDTO, String> {
}
