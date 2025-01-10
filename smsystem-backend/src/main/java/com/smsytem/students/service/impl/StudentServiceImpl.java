package com.smsytem.students.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.ClassDTO;
import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.dto.StudentDetailedDTO;
import com.smsytem.students.dto.StudentWithClassDTO;
import com.smsytem.students.entity.ClassOrSection;
import com.smsytem.students.entity.Student;
import com.smsytem.students.entity.Subject;
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
                    
                    // Add class name if student has a class assigned
                    if (student.getStudentClass() != null) {
                        studentDTO.setClassName(student.getStudentClass().getClassName());
                    }
                    
                    return studentDTO;
                })
                .collect(Collectors.toList());
}


    @Override
    public StudentDTO getStudentById(Long id) {
        Student theStudent = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student is not exist with the given id: " + id));
        theStudent.calculateFeesDue();
        
        StudentDTO studentDTO = modelMapper.map(theStudent, StudentDTO.class);
        
        // Add class name if student has a class assigned
        if (theStudent.getStudentClass() != null) {
            studentDTO.setClassName(theStudent.getStudentClass().getClassName());
        }
        
        return studentDTO;
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
    public StudentDetailedDTO getStudentDetailedById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student is not exist with the given id: " + id));
        student.calculateFeesDue();
        
        StudentDetailedDTO detailedDTO = modelMapper.map(student, StudentDetailedDTO.class);
        
        // Add detailed class information if student has a class assigned
        if (student.getStudentClass() != null) {
            ClassOrSection classInfo = student.getStudentClass();
            detailedDTO.setClassName(classInfo.getClassName());
            detailedDTO.setClassDescription(classInfo.getDescriptions());
            
            if (classInfo.getClassTeacher() != null) {
                detailedDTO.setClassTeacherID(classInfo.getClassTeacher().getTeacherID());
                detailedDTO.setClassTeacherName(
                    classInfo.getClassTeacher().getFirstName() + " " + 
                    classInfo.getClassTeacher().getLastName()
                );
            }
            
            if (classInfo.getSubjects() != null) {
                Set<Long> subjectIDs = classInfo.getSubjects().stream()
                        .map(Subject::getSubjectID)
                        .collect(Collectors.toSet());
                detailedDTO.setSubjectIDs(subjectIDs);
            }
        }
        
        return detailedDTO;
    }

    @Override
    public List<StudentDTO> getStudentsByClassId(Long classId) {
        List<Student> students = studentRepository.findAll().stream()
                .filter(student -> student.getStudentClass() != null && 
                        student.getStudentClass().getClassID().equals(classId))
                .collect(Collectors.toList());
        
        return students.stream()
                .map(student -> {
                    student.calculateFeesDue();
                    StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
                    if (student.getStudentClass() != null) {
                        studentDTO.setClassName(student.getStudentClass().getClassName());
                    }
                    return studentDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public StudentWithClassDTO getStudentWithPopulatedClass(Long studentId) {
        // This is like MongoDB populate - fetches related data in one query
        Student student = studentRepository.findStudentWithClassDetails(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));
        
        student.calculateFeesDue();
        
        StudentWithClassDTO dto = new StudentWithClassDTO();
        
        // Map basic student info
        dto.setStudentID(student.getStudentID());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        dto.setRoll(student.getRoll());
        dto.setHeight(student.getHeight());
        dto.setDateOfBirth(student.getDateOfBirth());
        dto.setTotalFees(student.getTotalFees());
        dto.setFeesPaid(student.getFeesPaid());
        dto.setFeesDue(student.getFeesDue());
        dto.setPhoneNumber(student.getPhoneNumber());
        dto.setImageLink(student.getImageLink());
        dto.setAddress(student.getAddress());
        dto.setCity(student.getCity());
        dto.setGuardianFirstName(student.getGuardianFirstName());
        dto.setGuardianLastName(student.getGuardianLastName());
        dto.setGuardianPhoneNumber(student.getGuardianPhoneNumber());
        dto.setGuardianEmail(student.getGuardianEmail());
        dto.setRelationship(student.getRelationship());
        
        // Populate class information (like MongoDB populate)
        if (student.getStudentClass() != null) {
            StudentWithClassDTO.ClassPopulatedInfo classInfo = new StudentWithClassDTO.ClassPopulatedInfo();
            classInfo.setClassID(student.getStudentClass().getClassID());
            classInfo.setClassName(student.getStudentClass().getClassName());
            classInfo.setDescriptions(student.getStudentClass().getDescriptions());
            
            // Populate teacher information
            if (student.getStudentClass().getClassTeacher() != null) {
                StudentWithClassDTO.ClassPopulatedInfo.TeacherBasicInfo teacherInfo = 
                    new StudentWithClassDTO.ClassPopulatedInfo.TeacherBasicInfo();
                teacherInfo.setTeacherID(student.getStudentClass().getClassTeacher().getTeacherID());
                teacherInfo.setFirstName(student.getStudentClass().getClassTeacher().getFirstName());
                teacherInfo.setLastName(student.getStudentClass().getClassTeacher().getLastName());
                teacherInfo.setEmail(student.getStudentClass().getClassTeacher().getEmail());
                teacherInfo.setQualification(student.getStudentClass().getClassTeacher().getQualification());
                
                classInfo.setClassTeacher(teacherInfo);
            }
            
            // Add subject IDs
            if (student.getStudentClass().getSubjects() != null) {
                Set<Long> subjectIDs = student.getStudentClass().getSubjects().stream()
                        .map(Subject::getSubjectID)
                        .collect(Collectors.toSet());
                classInfo.setSubjectIDs(subjectIDs);
            }
            
            dto.setClassInfo(classInfo);
        }
        
        return dto;
    }

    @Override
    public List<StudentWithClassDTO> getAllStudentsWithPopulatedClass() {
        // Fetch all students with related data in one query (like MongoDB populate)
        List<Student> students = studentRepository.findAllStudentsWithClassDetails();
        
        return students.stream()
                .map(student -> {
                    student.calculateFeesDue();
                    return mapToStudentWithClassDTO(student);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentWithClassDTO> searchStudentsByNameWithClass(String name) {
        List<Student> students = studentRepository.findStudentsByNameWithClassDetails(name);
        
        return students.stream()
                .map(student -> {
                    student.calculateFeesDue();
                    return mapToStudentWithClassDTO(student);
                })
                .collect(Collectors.toList());
    }

    /**
     * Helper method to map Student entity to StudentWithClassDTO
     * This is like MongoDB populate mapping
     */
    private StudentWithClassDTO mapToStudentWithClassDTO(Student student) {
        StudentWithClassDTO dto = modelMapper.map(student, StudentWithClassDTO.class);
        
        // Populate class information
        if (student.getStudentClass() != null) {
            StudentWithClassDTO.ClassPopulatedInfo classInfo = 
                modelMapper.map(student.getStudentClass(), StudentWithClassDTO.ClassPopulatedInfo.class);
            
            // Populate teacher information
            if (student.getStudentClass().getClassTeacher() != null) {
                StudentWithClassDTO.ClassPopulatedInfo.TeacherBasicInfo teacherInfo = 
                    modelMapper.map(student.getStudentClass().getClassTeacher(), 
                                  StudentWithClassDTO.ClassPopulatedInfo.TeacherBasicInfo.class);
                classInfo.setClassTeacher(teacherInfo);
            }
            
            // Add subject IDs
            if (student.getStudentClass().getSubjects() != null) {
                Set<Long> subjectIDs = student.getStudentClass().getSubjects().stream()
                        .map(Subject::getSubjectID)
                        .collect(Collectors.toSet());
                classInfo.setSubjectIDs(subjectIDs);
            }
            
            dto.setClassInfo(classInfo);
        }
        
        return dto;
    }

    @Override
    public void deleteStudent(Long id) {
        Student studentToDelete = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        studentRepository.deleteById(id);
        modelMapper.map(studentToDelete, StudentDTO.class);
    }
}
