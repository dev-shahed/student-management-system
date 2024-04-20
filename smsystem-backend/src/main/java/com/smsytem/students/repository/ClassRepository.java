package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.ClassOrSection;

public interface ClassRepository extends JpaRepository<ClassOrSection, Long> {
    
}
