package com.smsytem.students.service;

import com.smsytem.students.dto.GuardianDTO;

public interface GuardianService {

    GuardianDTO addGuardian(GuardianDTO guardianDTO);

    GuardianDTO getGuardianById(Long id);

    void deleteGuardian(Long id);

}
