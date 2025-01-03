package com.smsytem.students.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Basic Class DTO without nested objects
 * Used for most API responses to avoid over-fetching
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassBasicDTO {
    private Long classID;
    private String className;
    private String descriptions;
    
    // Reference IDs only - no nested objects
    private Long teacherID;
    private String teacherName; // Just the name, not the full object
    private Set<Long> subjectIDs;
    private Integer totalStudents; // Count of students in this class
}
