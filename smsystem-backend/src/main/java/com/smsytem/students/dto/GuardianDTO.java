package com.smsytem.students.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GuardianDTO {
    private Long guardianID;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String relationship;
}
