package com.smsytem.students.mapper;

import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.entity.Student;

public class StudentMapper {
    public static StudentDTO mapToStudentDto(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getStudentClass(),
                student.getRoll(),
                student.getHeight(),
                student.getDateOfBirth(),
                student.getTotalFees(),
                student.getFeesPaid(),
                student.getFeesDue(),
                student.getPhoneNumber(),
                student.getImageLink());
    }

    public static Student mapToStudent(StudentDTO studentDTO) {
        return new Student(
                studentDTO.getId(),
                studentDTO.getName(),
                studentDTO.getStudentClass(),
                studentDTO.getRoll(),
                studentDTO.getHeight(),
                studentDTO.getDateOfBirth(),
                studentDTO.getTotalFees(),
                studentDTO.getFeesPaid(),
                studentDTO.getFeesDue(),
                studentDTO.getPhoneNumber(),
                studentDTO.getImageLink());
    }
}
