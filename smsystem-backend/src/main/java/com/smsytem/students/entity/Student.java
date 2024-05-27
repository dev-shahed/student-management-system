package com.smsytem.students.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentID;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "class_id", nullable = false)
    private Long classID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_class")
    private ClassOrSection studentClass;

    @Column(name = "roll_number", nullable = false, unique = true)
    private int roll;

    @Column(name = "height")
    private double height;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "total_fees")
    private double totalFees;

    @Column(name = "fees_paid")
    private double feesPaid;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    // Guardian Information
    @Column(name = "guardian_first_name", nullable = false)
    private String guardianFirstName;

    @Column(name = "guardian_last_name", nullable = false)
    private String guardianLastName;

    @Column(name = "guardian_phone_number", nullable = false)
    private String guardianPhoneNumber;

    @Column(name = "guardian_email", nullable = false)
    private String guardianEmail;

    @Column(name = "guardian_relationship", nullable = false)
    private String relationship;

    @Transient
    private double feesDue;

    public void calculateFeesDue() {
        this.feesDue = (totalFees - feesPaid);
    }
}