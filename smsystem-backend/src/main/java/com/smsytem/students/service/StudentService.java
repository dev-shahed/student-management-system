package com.smsytem.students.service;

import java.util.List;

import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.dto.StudentDetailedDTO;
import com.smsytem.students.dto.StudentWithClassDTO;

/**
 * Service interface for Student management
 * Enhanced with methods for basic and detailed student information
 */
public interface StudentService {
    
    StudentDTO createStudent(StudentDTO studentDTO);

    List<StudentDTO> getAllStudents();

    StudentDTO getStudentById(Long id);
    
    /**
     * Get detailed student information including class details
     */
    StudentDetailedDTO getStudentDetailedById(Long id);

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);

    void deleteStudent(Long id);
    
    /**
     * Get students by class ID
     */
    List<StudentDTO> getStudentsByClassId(Long classId);
    
    /**
     * MongoDB populate equivalent: Get student with populated class and teacher data
     * This fetches related data in a single query (like MongoDB populate)
     */
    StudentWithClassDTO getStudentWithPopulatedClass(Long studentId);
    
    /**
     * MongoDB populate equivalent: Get all students with populated class data
     */
    List<StudentWithClassDTO> getAllStudentsWithPopulatedClass();
    
    /**
     * Search students by name with populated class details
     */
    List<StudentWithClassDTO> searchStudentsByNameWithClass(String name);
}
