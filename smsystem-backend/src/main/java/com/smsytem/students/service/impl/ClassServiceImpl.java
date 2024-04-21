package com.smsytem.students.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.ClassDTO;
import com.smsytem.students.entity.ClassOrSection;
import com.smsytem.students.entity.Teacher;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.ClassRepository;
import com.smsytem.students.repository.TeacherRepository;
import com.smsytem.students.service.ClassOrSectionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ClassServiceImpl implements ClassOrSectionService {

    private ClassRepository classRepository;
    private TeacherRepository teacherRepository;
    private ModelMapper modelMapper;

    @Override
    public ClassDTO addClass(ClassDTO classDTO) {
        ClassOrSection stuClass = modelMapper.map(classDTO, ClassOrSection.class);
        Teacher teacher = teacherRepository.findById(classDTO.getTeacherID())
                .orElseThrow(() -> new ResourceNotFoundException("teacher dones't exits!"));
        stuClass.setClassTeacher(teacher);
        ClassOrSection savedClass = classRepository.save(stuClass);
        ClassDTO saveClassDTO = modelMapper.map(savedClass, ClassDTO.class);
        return saveClassDTO;
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
