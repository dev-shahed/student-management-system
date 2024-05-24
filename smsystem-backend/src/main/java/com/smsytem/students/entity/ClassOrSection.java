package com.smsytem.students.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
    @JoinColumn(name = "class_teacher", nullable = false)
    private Teacher classTeacher;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "class_subject", joinColumns = @JoinColumn(name = "class_id"), inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects = new HashSet<>();
}
