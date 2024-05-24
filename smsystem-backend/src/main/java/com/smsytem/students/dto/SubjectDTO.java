package com.smsytem.students.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smsytem.students.entity.Teacher;

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
    private Long teacherID;
    @JsonIgnoreProperties({"joiningDate", "salary", "salaryStatus", "imageLink", "address", "nationality"})
    private Teacher thoughtBy;
    private Set<String> days;
}
