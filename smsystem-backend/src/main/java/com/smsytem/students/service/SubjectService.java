package com.smsytem.students.service;

import java.util.List;

import com.smsytem.students.dto.SubjectDTO;

public interface SubjectService {
    SubjectDTO addSubject(SubjectDTO subjectDTO);

    // List<SubjectDTO> getSubjectById(Long id);

    List<SubjectDTO> getAllSubjects();

    // void deleteSubject(Long id);
}
