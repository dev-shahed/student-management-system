package com.smsytem.students.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private Long teacherID;
    @JsonIgnoreProperties({"joiningDate", "salary", "salaryStatus", "imageLink", "address", "nationality"})
    private TeacherDTO classTeacher;
}
