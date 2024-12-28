package com.smsytem.students.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smsytem.students.entity.Role;
import com.smsytem.students.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Data Initializer component to ensure required roles exist in the database
 * This component runs at application startup to initialize essential data
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing application data...");
        
        // Initialize roles
        initializeRoles();
        
        log.info("Application data initialization completed successfully");
    }

    /**
     * Initialize required roles in the database
     */
    private void initializeRoles() {
        log.info("Initializing roles...");
        
        List<String> requiredRoles = Arrays.asList(
            "ROLE_ADMIN",        // System administrators with full access
            "ROLE_PRINCIPAL",    // School principal with management access
            "ROLE_TEACHER",      // Teachers with academic management access
            "ROLE_STUDENT",      // Students with limited access to their data
            "ROLE_PARENT",       // Parents with access to their children's data
            "ROLE_ACCOUNTANT",   // Accountants with financial management access
            "ROLE_LIBRARIAN",    // Librarians with library management access
            "ROLE_RECEPTIONIST", // Receptionists with basic information access
            "ROLE_CLERK"         // Office clerks with administrative access
        );

        for (String roleName : requiredRoles) {
            if (!roleRepository.existsByName(roleName)) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                log.info("Created role: {}", roleName);
            } else {
                log.debug("Role already exists: {}", roleName);
            }
        }
        
        log.info("Roles initialization completed");
    }
}
