package com.smsytem.students.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassDTO {
    private Long classID;
    private String className;
    private String descriptions;
    @JsonInclude(Include.NON_NULL)
    private Long teacherID;
    private TeacherDTO classTeacher;
}
