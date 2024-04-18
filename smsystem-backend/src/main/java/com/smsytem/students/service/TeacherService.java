package com.smsytem.students.service;

import java.util.List;

import com.smsytem.students.dto.TeacherDTO;

public interface TeacherService {
    TeacherDTO addTeacher(TeacherDTO teacherDTO);

    List<TeacherDTO> allTeacher();

    TeacherDTO getTeacherById(Long id);

    TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO);

    void deleteTeacher(Long id);
}
