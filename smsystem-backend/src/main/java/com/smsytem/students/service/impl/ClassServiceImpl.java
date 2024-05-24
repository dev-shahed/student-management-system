package com.smsytem.students.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.ClassDTO;
import com.smsytem.students.entity.ClassOrSection;
import com.smsytem.students.entity.Subject;
import com.smsytem.students.entity.Teacher;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.ClassRepository;
import com.smsytem.students.repository.SubjectRepository;
import com.smsytem.students.repository.TeacherRepository;
import com.smsytem.students.service.ClassOrSectionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClassServiceImpl implements ClassOrSectionService {

    private ClassRepository classRepository;
    private TeacherRepository teacherRepository;
    private SubjectRepository subjectRepository;
    private ModelMapper modelMapper;

    @Override
    public ClassDTO addClass(ClassDTO classDTO) {
        ClassOrSection theClass = modelMapper.map(classDTO, ClassOrSection.class);
        // Convert subject IDs to Subject entities
        Set<Subject> subjects = new HashSet<>();
        for (Long subjectID : classDTO.getSubjectIDs()) {
            Subject subject = subjectRepository.findById(subjectID)
                    .orElseThrow(() -> new ResourceNotFoundException("Subject doesn't exist!"));
            subjects.add(subject);
        }
        theClass.setSubjects(subjects);
        Teacher teacher = teacherRepository.findById(classDTO.getTeacherID())
                .orElseThrow(() -> new ResourceNotFoundException("teacher doesn't exits!"));
        theClass.setClassTeacher(teacher);
        ClassOrSection savedClass = classRepository.save(theClass);
        ClassDTO savedClassDTO = modelMapper.map(savedClass, ClassDTO.class);
        savedClassDTO.setSubjectIDs(classDTO.getSubjectIDs()); // Set the subjectIDs in the DTO
        return savedClassDTO;
    }

    @Override
    public ClassDTO getClassById(Long id) {
        ClassOrSection theClass = classRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Class is not exist with the given id: " + id));
        return modelMapper.map(theClass, ClassDTO.class);
    }

    @Override
    public void deleteClass(Long id) {
        ClassOrSection theClass = classRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Class is not exist with the given id: " + id));
        classRepository.deleteById(id);
        modelMapper.map(theClass, ClassDTO.class);
    }

}
