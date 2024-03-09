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
        //update all students feedDue column
        students.forEach(Student::calculateFeesDue);
        return students.stream().map(StudentMapper::mapToStudentDto).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student theStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student is not exist with the given id: " + id));
                theStudent.calculateFeesDue();
        return StudentMapper.mapToStudentDto(theStudent);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student is not exist with the given id: " + id));

        student.setName(studentDTO.getName());
        student.setStudentClass(studentDTO.getStudentClass());
        student.setRoll(studentDTO.getRoll());
        student.setFeesPaid(studentDTO.getFeesPaid());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        Student updatedStudent = studentRepository.save(student);
        return StudentMapper.mapToStudentDto(updatedStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student studentToDelete = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        studentRepository.deleteById(id);
        StudentMapper.mapToStudentDto(studentToDelete);
    }
}
