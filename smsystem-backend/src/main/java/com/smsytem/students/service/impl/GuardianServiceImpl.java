package com.smsytem.students.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.GuardianDTO;
import com.smsytem.students.entity.Guardian;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.GuardianRepository;
import com.smsytem.students.service.GuardianService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class GuardianServiceImpl implements GuardianService {

    private GuardianRepository guardianRepository;
    private ModelMapper modelMapper;

    @Override
    public GuardianDTO addGuardian(GuardianDTO guardianDTO) {
        Guardian guardian = modelMapper.map(guardianDTO, Guardian.class);
        Guardian savedGuardian = guardianRepository.save(guardian);
        GuardianDTO saveGuardianDTO = modelMapper.map(savedGuardian, GuardianDTO.class);
        return saveGuardianDTO;
    }

    @Override
    public GuardianDTO getGuardianById(Long id) {
        Guardian theGuardian = guardianRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Guardian is not exist with the given id: " + id));
        return modelMapper.map(theGuardian, GuardianDTO.class);
    }

    @Override
    public void deleteGuardian(Long id) {
        Guardian theGuardian = guardianRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Guardian is not exist with the given id: " + id));
        guardianRepository.deleteById(id);
        modelMapper.map(theGuardian, GuardianDTO.class);
    }

}
