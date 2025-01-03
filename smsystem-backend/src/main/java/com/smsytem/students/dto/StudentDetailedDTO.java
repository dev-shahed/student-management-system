package com.smsytem.students.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Detailed Student DTO with additional information
 * Used when full student details with class info are needed
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetailedDTO {
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
    
    // Class information (flattened, not nested)
    private Long classID;
    private String className;
    private String classDescription;
    private Long classTeacherID;
    private String classTeacherName;
    private Set<Long> subjectIDs;
    
    // Guardian Information
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianPhoneNumber;
    private String guardianEmail;
    private String relationship;
}
