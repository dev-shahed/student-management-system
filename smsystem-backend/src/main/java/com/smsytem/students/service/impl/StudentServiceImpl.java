package com.smsytem.students.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.entity.Student;
import com.smsytem.students.exception.ResourceNotFoundException;
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

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No student found! Please add student");
        }
        return students.stream().map(StudentMapper::mapToStudentDto).collect(Collectors.toList());
    }

}
