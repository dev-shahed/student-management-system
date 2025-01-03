package com.smsytem.students.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO that represents MongoDB-style "populated" data
 * Contains student data with populated class and teacher information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentWithClassDTO {
    // Student basic info
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
    
    // Guardian Information
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianPhoneNumber;
    private String guardianEmail;
    private String relationship;
    
    // Populated class information (like MongoDB populate)
    private ClassPopulatedInfo classInfo;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ClassPopulatedInfo {
        private Long classID;
        private String className;
        private String descriptions;
        
        // Populated teacher information
        private TeacherBasicInfo classTeacher;
        
        // Subject IDs (you could populate full subject info if needed)
        private Set<Long> subjectIDs;
        
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class TeacherBasicInfo {
            private Long teacherID;
            private String firstName;
            private String lastName;
            private String email;
            private String qualification;
        }
    }
}
