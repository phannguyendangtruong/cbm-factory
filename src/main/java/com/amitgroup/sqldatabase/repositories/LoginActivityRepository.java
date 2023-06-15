package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.LoginActivity;

@Repository
public interface LoginActivityRepository extends JpaRepository<LoginActivity, Long> {
    
}
