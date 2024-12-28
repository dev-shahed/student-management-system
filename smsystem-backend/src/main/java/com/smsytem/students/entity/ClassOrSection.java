package com.smsytem.students.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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
