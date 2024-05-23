package com.smsytem.students.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.SubjectDTO;
import com.smsytem.students.entity.Subject;
import com.smsytem.students.entity.Teacher;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.SubjectRepository;
import com.smsytem.students.repository.TeacherRepository;
import com.smsytem.students.service.SubjectService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;
    private TeacherRepository teacherRepository;
    private ModelMapper modelMapper;

    @Override
    public SubjectDTO addSubject(SubjectDTO subjectDTO) {
        Subject sub = modelMapper.map(subjectDTO, Subject.class);
        Teacher teacher = teacherRepository.findById(subjectDTO.getThoughtBy().getTeacherID())
                .orElseThrow(() -> new ResourceNotFoundException("teacher doesn't exits!"));
        sub.setThoughtBy(teacher);
        Subject savedSubject = subjectRepository.save(sub);
        SubjectDTO saveSubjectDTO = modelMapper.map(savedSubject, SubjectDTO.class);
        return saveSubjectDTO;
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        if (subjects.isEmpty()) {
            throw new ResourceNotFoundException("No subjects found! Please add subject");
        }
        return subjects.stream().map(sub -> modelMapper.map(sub, SubjectDTO.class))
                .collect(Collectors.toList());
    }

}
