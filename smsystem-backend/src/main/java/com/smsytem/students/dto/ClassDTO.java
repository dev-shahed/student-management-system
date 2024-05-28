package com.smsytem.students.dto;

import java.util.Collections;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long teacherID;
    @JsonIgnoreProperties({ "joiningDate", "salary", "salaryStatus", "imageLink", "address", "nationality" })
    private TeacherDTO classTeacher;
    @JsonInclude(Include.NON_NULL)
    private Set<Long> subjectIDs;

    public Set<Long> getSubjectIDs() {
        return subjectIDs != null ? subjectIDs : Collections.emptySet();
    }

}
