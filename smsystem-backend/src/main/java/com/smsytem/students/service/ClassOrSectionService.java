package com.smsytem.students.service;

import com.smsytem.students.dto.ClassDTO;


public interface ClassOrSectionService {

    ClassDTO addClass(ClassDTO classDTO);

    ClassDTO getClassById(Long id);

    void deleteClass(Long id);

}
