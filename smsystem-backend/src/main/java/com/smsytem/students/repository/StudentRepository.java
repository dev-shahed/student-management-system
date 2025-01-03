package com.smsytem.students.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smsytem.students.entity.ClassOrSection;
import com.smsytem.students.entity.Student;

/**
 * Repository interface for Student entity
 * Enhanced with JOIN queries for fetching related data (similar to MongoDB populate)
 */
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find students by class ID
     */
    List<Student> findByStudentClassClassID(Long classId);

    /**
     * MongoDB populate equivalent: Fetch student with class details using JOIN
     * This is like populate() in MongoDB - fetches related data in one query
     */
    @Query("SELECT s FROM Student s " +
           "LEFT JOIN FETCH s.studentClass c " +
           "LEFT JOIN FETCH c.classTeacher t " +
           "WHERE s.studentID = :studentId")
    Optional<Student> findStudentWithClassDetails(@Param("studentId") Long studentId);

    /**
     * Fetch multiple students with their class information
     * Similar to MongoDB's populate for arrays
     */
    @Query("SELECT s FROM Student s " +
           "LEFT JOIN FETCH s.studentClass c " +
           "LEFT JOIN FETCH c.classTeacher t")
    List<Student> findAllStudentsWithClassDetails();

    /**
     * Fetch students by class with all related data
     * Like MongoDB populate with filtering
     */
    @Query("SELECT s FROM Student s " +
           "LEFT JOIN FETCH s.studentClass c " +
           "LEFT JOIN FETCH c.classTeacher t " +
           "LEFT JOIN FETCH c.subjects sub " +
           "WHERE c.classID = :classId")
    List<Student> findStudentsWithFullClassDetails(@Param("classId") Long classId);

    /**
     * Custom query to get class information by class ID
     * Used in existing service logic
     */
    @Query("SELECT c FROM ClassOrSection c WHERE c.classID = :classID")
    ClassOrSection findClassByClassID(@Param("classID") Long classID);

    /**
     * Find students with attendance count (JOIN with another table)
     */
    @Query("SELECT s, COUNT(a) as attendanceCount FROM Student s " +
           "LEFT JOIN Attendance a ON s.studentID = a.student.studentID " +
           "WHERE s.studentClass.classID = :classId " +
           "GROUP BY s.studentID")
    List<Object[]> findStudentsWithAttendanceCount(@Param("classId") Long classId);

    /**
     * Search students by name with class details
     */
    @Query("SELECT s FROM Student s " +
           "LEFT JOIN FETCH s.studentClass c " +
           "WHERE LOWER(s.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(s.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Student> findStudentsByNameWithClassDetails(@Param("name") String name);
}