package com.smsytem.students.service;

import java.util.List;

import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.dto.StudentDetailedDTO;
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

}