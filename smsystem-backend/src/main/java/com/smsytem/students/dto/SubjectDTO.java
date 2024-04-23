package com.smsytem.students.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubjectDTO {
    private Long subjectID;
    private String subjectName;
    private String descriptions;
    private TeacherDTO thoughtBy;
    private Long classID;
    private Set<String> days;

    // Getters and setters
}
