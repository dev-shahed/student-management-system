package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smsytem.students.entity.ClassOrSection;
import com.smsytem.students.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT c FROM ClassOrSection c WHERE c.classID = :classID")
    public ClassOrSection findClassByClassID(@Param("classID") Long classID);
}