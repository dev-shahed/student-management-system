package com.smsytem.students.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @JsonIgnoreProperties({"joiningDate", "salary", "salaryStatus", "imageLink", "address", "nationality"})
    private TeacherDTO thoughtBy;
    private Long classID;
    private Set<String> days;
}
