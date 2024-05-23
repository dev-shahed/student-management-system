package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.Subject;

/**
 * SubjectRepository
 */
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}