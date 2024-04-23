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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thought_by", nullable = false)
    private Teacher thoughtBy;

    // Assuming a subject can be taught to multiple classes
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "class_subject",
               joinColumns = @JoinColumn(name = "subject_id"),
               inverseJoinColumns = @JoinColumn(name = "class_id"))
    private Set<ClassOrSection> classes = new HashSet<>();

    // Assuming a subject can be taught on specific days
    @ElementCollection
    @CollectionTable(name = "subject_days",
                     joinColumns = @JoinColumn(name = "subject_id"))
    @Column(name = "day")
    private Set<String> days = new HashSet<>();

    // Getters and setters
}
