package com.smsytem.students.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherDTO {
    private Long teacherID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String qualification;
    private double experience;
    private LocalDate joiningDate;
    private double salary;
    private String salaryStatus;
    private String imageLink;
    private String address;
    private String nationality;
}
