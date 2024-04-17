package com.smsytem.students.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.entity.Student;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.StudentRepository;
import com.smsytem.students.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        Student savedStudent = studentRepository.save(student);
        StudentDTO savedStudentDTO = modelMapper.map(savedStudent, StudentDTO.class);
        return savedStudentDTO;
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No student found! Please add student");
        }
        // update all students feedDue column
        students.forEach(Student::calculateFeesDue);
        return students.stream().map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Student theStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student is not exist with the given id: " + id));
        theStudent.calculateFeesDue();
        return modelMapper.map(theStudent, StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student is not exist with the given id: " + id));

        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setStudentClass(studentDTO.getStudentClass());
        student.setRoll(studentDTO.getRoll());
        student.setFeesPaid(studentDTO.getFeesPaid());
        student.setPhoneNumber(studentDTO.getPhoneNumber());
        student.calculateFeesDue();
        Student updatedStudent = studentRepository.save(student);
        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    @Override
    public void deleteStudent(Long id) {
        Student studentToDelete = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        studentRepository.deleteById(id);
        modelMapper.map(studentToDelete, StudentDTO.class);
    }
}
