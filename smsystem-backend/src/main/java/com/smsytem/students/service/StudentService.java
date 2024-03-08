package com.smsytem.students.service;

import java.util.List;

import com.smsytem.students.dto.StudentDTO;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);

    List<StudentDTO> getAllStudents();
}
