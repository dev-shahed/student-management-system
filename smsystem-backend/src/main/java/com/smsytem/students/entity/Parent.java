package com.smsytem.students.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

/**
 * Entity representing parent/guardian information
 * Links parents to their children (students) and user accounts
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "parents")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Link to user account for authentication

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "alternate_phone")
    private String alternatePhone;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_relationship")
    private String emergencyRelationship;

    // Many-to-many relationship with students (parent can have multiple children)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "parent_student",
        joinColumns = @JoinColumn(name = "parent_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> children = new HashSet<>();

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    /**
     * Enum for gender
     */
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
