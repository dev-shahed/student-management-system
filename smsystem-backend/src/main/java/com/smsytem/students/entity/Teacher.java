package com.smsytem.students.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherID;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email",  nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "qualification", nullable = false)
    private String qualification;

    @Column(name = "teaching_experience", nullable = false)
    private double experience;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "salary_status", nullable = false)
    private String salaryStatus;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "nationality")
    private String nationality;
}
