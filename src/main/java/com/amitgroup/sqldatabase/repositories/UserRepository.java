package com.amitgroup.sqldatabase.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.amitgroup.sqldatabase.entities.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1 and u.isActive = true")
    User findByEmail(String email);
    @Query ("SELECT u FROM User u WHERE u.username = ?1 and u.isActive = true")
    User findByUsername(String username);
    //find all by name desc and isActive = true
    @Query("SELECT u FROM User u ORDER BY u.id ASC")
    Page<User> findAllByFullNameDesc(Pageable pageable);
    Page<User> findAll(Pageable pageable);
    Page<User> findByFullNameContainingIgnoreCase(String keyword, Pageable pageable);

    //find by roleId = 4
    @Query("SELECT u FROM User u WHERE u.role.id = 3")
    Page<User> findByRoleWorker(Pageable pageable);
}
