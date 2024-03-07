package com.smsytem.students.service.impl;

import org.springframework.stereotype.Service;

import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.entity.Student;
import com.smsytem.students.mapper.StudentMapper;
import com.smsytem.students.repository.StudentRepository;
import com.smsytem.students.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.mapToStudent(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(savedStudent);
    }

}
