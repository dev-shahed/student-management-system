package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.Role;

/**
 * Repository interface for Role entity
 * Provides custom query methods for role management
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    /**
     * Find role by name
     */
    Role findByName(String name);
    
    /**
     * Check if role exists by name
     */
    boolean existsByName(String name);
}
