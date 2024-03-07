package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
    

}