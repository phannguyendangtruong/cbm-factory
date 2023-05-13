package com.factory.redis.repositories;

import org.springframework.data.repository.CrudRepository;

import com.factory.redis.entities.VerificationRequestDTO;

public interface VerificationRequestDTORepository extends CrudRepository<VerificationRequestDTO, String> {
}
