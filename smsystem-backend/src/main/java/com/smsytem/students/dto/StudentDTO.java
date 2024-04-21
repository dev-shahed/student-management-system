package com.smsytem.students.dto;

import java.time.LocalDate;

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
public class StudentDTO {
    private Long studentID;
    private String firstName;
    private String lastName;
    private String email;
    @JsonInclude(Include.NON_NULL)
    private Long classID;
    private int roll;
    private double height;
    private LocalDate dateOfBirth;
    private double totalFees;
    private double feesPaid;
    private double feesDue;
    private String phoneNumber;
    private String imageLink;
    private String address;
    private String city;

    // Include a reference to the class object
    private ClassDTO studentClass;
    // Guardian Information
    private String guardianFirstName;
    private String guardianLastName;
    private String guardianPhoneNumber;
    private String guardianEmail;
    private String Relationship;
}
