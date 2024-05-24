package com.smsytem.students.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectID;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "descriptions")
    private String descriptions;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thought_by")
    private Teacher thoughtBy;

    @ElementCollection
    @CollectionTable(name = "subject_days", joinColumns = @JoinColumn(name = "subject_id"))
    @Column(name = "day")
    private Set<String> days = new HashSet<>();
}
