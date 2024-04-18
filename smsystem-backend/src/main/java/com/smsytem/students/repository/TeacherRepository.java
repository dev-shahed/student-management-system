package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}