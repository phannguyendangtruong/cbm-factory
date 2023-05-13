package com.factory.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.factory.sqldatabase.entities.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
}
