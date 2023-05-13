package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.Machine;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
}
