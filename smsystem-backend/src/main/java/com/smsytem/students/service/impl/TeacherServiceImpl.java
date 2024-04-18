package com.smsytem.students.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.TeacherDTO;
import com.smsytem.students.entity.Teacher;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.TeacherRepository;
import com.smsytem.students.service.TeacherService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepository teacherRepository;
    private ModelMapper modelMapper;

    @Override
    public TeacherDTO addTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = modelMapper.map(teacherDTO, Teacher.class);
        Teacher savedTeacher = teacherRepository.save(teacher);
        TeacherDTO savedTeacherDTO = modelMapper.map(savedTeacher, TeacherDTO.class);
        return savedTeacherDTO;
    }

    @Override
    public List<TeacherDTO> allTeacher() {
        List<Teacher> teachers = teacherRepository.findAll();
        if (teachers.isEmpty()) {
            throw new ResourceNotFoundException("No teacher found!");
        }
        return teachers.stream().map(teacher -> modelMapper.map(teacher, TeacherDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO getTeacherById(Long id) {
        Teacher theTeacher = teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Teacher is not exist with the given id: " + id));
        return modelMapper.map(theTeacher, TeacherDTO.class);
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) {
        Teacher theTeacher = teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Teacher is not exist with the given id: " + id));
        theTeacher.setSalary(teacherDTO.getSalary());
        theTeacher.setEmail(teacherDTO.getEmail());
        theTeacher.setQualification(teacherDTO.getQualification());
        theTeacher.setExperience(teacherDTO.getExperience());
        theTeacher.setSalaryStatus(teacherDTO.getSalaryStatus());
        Teacher updatedTeacher = teacherRepository.save(theTeacher);
        return modelMapper.map(updatedTeacher, TeacherDTO.class);
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher deleteTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("teacher not found with ID: " + id));
        teacherRepository.deleteById(id);
        modelMapper.map(deleteTeacher, TeacherDTO.class);
    }

}
