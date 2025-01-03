package com.smsytem.students.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Basic Student DTO without nested objects
 * Used for most API responses to avoid over-fetching
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentBasicDTO {
    private Long studentID;
    private String firstName;
    private String lastName;
    private String email;
    private int roll;
    private double height;
    private LocalDate dateOfBirth;
    private double totalFees;
    private double feesPaid;
    private double feesDue;
    private String phoneNumber;
    private String imageLink;
    private String address;
    private String city;
    
    // Reference IDs only - no nested objects
    private Long classID;
    private String className; // Just the name, not the full object
    
    // Guardian Information
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianPhoneNumber;
    private String guardianEmail;
    private String relationship;
}
