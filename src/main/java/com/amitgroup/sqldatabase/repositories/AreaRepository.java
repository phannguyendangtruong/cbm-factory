package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.Area;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
}
