package com.smsytem.students.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Basic Teacher DTO without sensitive information
 * Used for public API responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherBasicDTO {
    private Long teacherID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String qualification;
    private double experience;
    private LocalDate joiningDate;
    // Removed salary and sensitive information
}
