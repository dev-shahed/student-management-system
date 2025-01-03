package com.smsytem.students.dto;

import java.util.Collections;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class DTO without nested objects - FIXED VERSION
 * Removed nested TeacherDTO to prevent circular references and over-fetching
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassDTO {
    private Long classID;
    private String className;
    private String descriptions;
    
    // Teacher information - flattened instead of nested
    private Long teacherID;
    private String teacherName; // Just the name, not the full TeacherDTO object
    
    private Set<Long> subjectIDs;
    private Integer totalStudents; // Count of students in this class

    public Set<Long> getSubjectIDs() {
        return subjectIDs != null ? subjectIDs : Collections.emptySet();
    }
}
