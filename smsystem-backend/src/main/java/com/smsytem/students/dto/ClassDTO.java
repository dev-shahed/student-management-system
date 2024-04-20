package com.smsytem.students.dto;

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
    // private Teacher classTeacher;

}
