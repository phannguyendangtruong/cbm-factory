package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.amitgroup.sqldatabase.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
