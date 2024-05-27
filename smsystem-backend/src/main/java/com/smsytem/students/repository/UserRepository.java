package com.smsytem.students.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<User> findByUsernameOrEmail(String username, String email);
}
