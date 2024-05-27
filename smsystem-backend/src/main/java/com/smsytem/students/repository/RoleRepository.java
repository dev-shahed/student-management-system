package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
