package com.smsytem.students.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "classes")
public class ClassOrSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classID;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "descriptions")
    private String descriptions;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_teacher")
    private Teacher classTeacher;
}