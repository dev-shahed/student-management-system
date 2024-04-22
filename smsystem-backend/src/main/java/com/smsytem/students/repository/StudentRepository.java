package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // @Query("SELECT s FROM Student s JOIN FETCH s.studentClass WHERE s.classId = :classId")
    // Student findByClassID(@Param("classId") Long classID);

    // @Query("SELECT s, c FROM Student s JOIN s.studentClass c")
    // List<Object[]> findAllStudentsWithClasses();
    

}