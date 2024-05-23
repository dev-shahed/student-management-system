package com.smsytem.students.service;

import java.util.List;

import com.smsytem.students.dto.SubjectDTO;

public interface SubjectService {
    SubjectDTO addSubject(SubjectDTO subjectDTO);

    SubjectDTO getSubjectById(Long id);

    List<SubjectDTO> getAllSubjects();

    void deleteSubject(Long id);
}
