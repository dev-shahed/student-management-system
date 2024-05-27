package com.smsytem.students.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smsytem.students.dto.LoginDTO;
import com.smsytem.students.dto.RegisterDTO;
import com.smsytem.students.entity.Role;
import com.smsytem.students.entity.User;
import com.smsytem.students.exception.AuthException;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.RoleRepository;
import com.smsytem.students.repository.UserRepository;
import com.smsytem.students.security.JwtTokenProvider;
import com.smsytem.students.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    private ModelMapper modelMapper;

    @Override
    public RegisterDTO register(RegisterDTO registerDTO) {

        // check username is already exist or not in DB
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new AuthException("username already exist!");
        }
        // check email in DB
        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new AuthException("email already exist!");
        }

        // User user = new User();
        // user.setName(registerDTO.getName());
        // user.setUsername(registerDTO.getUsername());
        // user.setEmail(registerDTO.getEmail());
        // user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        // Mapping properties from RegisterDTO to User
        User user = modelMapper.map(registerDTO, User.class);

        // Encode the password
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");// default role USER for every registered users..
        roles.add(userRole); // add registered user

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, RegisterDTO.class);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsernameOrEmail(),
                            loginDTO.getPassword()));

            // Set the authenticated authentication object in the SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // jwt login with token
            String token = jwtTokenProvider.generateJwtToken(authentication);

            // If authentication succeeds, return a success message
            return token;
        } catch (BadCredentialsException e) {
            throw new ResourceNotFoundException("User not found");
        }
    }

}
