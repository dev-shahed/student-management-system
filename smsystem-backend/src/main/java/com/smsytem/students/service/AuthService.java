package com.smsytem.students.service;

import com.smsytem.students.dto.LoginDTO;
import com.smsytem.students.dto.RegisterDTO;

public interface AuthService {
    RegisterDTO register(RegisterDTO registerDTO);

    String login(LoginDTO loginDTO);
}
