package com.smsytem.students.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object for user registration
 * Enhanced with role selection capability
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<String> roles; // Allow multiple roles during registration
}
