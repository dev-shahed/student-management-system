package com.smsytem.students.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.ClassDTO;
import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.entity.ClassOrSection;
import com.smsytem.students.entity.Student;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.ClassRepository;
import com.smsytem.students.repository.StudentRepository;
import com.smsytem.students.service.StudentService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private StudentRepository studentRepository;
    private ClassRepository classRepository;
    private ModelMapper modelMapper;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        ClassOrSection classOrSection = classRepository.findById(studentDTO.getClassID())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "class doesn't exist with the id " + studentDTO.getClassID()));
        student.setStudentClass(classOrSection);
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
    // Update all students feesDue column
    students.forEach(Student::calculateFeesDue);
    return students.stream()
                .map(student -> {
                    StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
                    // Fetch class information using the provided class ID
                    ClassOrSection classInfo = studentRepository.findClassByClassID(studentDTO.getClassID());

                    // Map classInfo to ClassDTO
                    ClassDTO classDTO = modelMapper.map(classInfo, ClassDTO.class);

                    // Set class information into StudentDTO
                    studentDTO.setStudentClass(classDTO);

                    return studentDTO;
                })
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
        student.setStudentID(studentDTO.getStudentID());
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
