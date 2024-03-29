package com.smsytem.students.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long id;

    @Column(name = "student_name", nullable = false)
    private String name;

    @Column(name = "student_class", nullable = false)
    private String studentClass;

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

    @Transient
    private double feesDue;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "image_link")
    private String imageLink;

    public void calculateFeesDue() {
        this.feesDue = (totalFees - feesPaid);
    }
}