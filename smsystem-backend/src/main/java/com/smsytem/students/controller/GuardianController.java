package com.smsytem.students.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsytem.students.dto.GuardianDTO;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.GuardianService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("api/guardians")
public class GuardianController {
    private GuardianService guardianService;

    @PostMapping()
    public ResponseEntity<?> creatingGuardian(@RequestBody GuardianDTO guardianDTO) {
        try {
            GuardianDTO createdGuardian = guardianService.addGuardian(guardianDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("guardian created successfully", createdGuardian));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create guardian: " + e.getMessage()));
        }
    }

    // retrieve single guardian
    @GetMapping("/{id}")
    public ResponseEntity<?> GuardianById(@PathVariable Long id, HttpServletRequest request) {
        try {
            GuardianDTO guardian = guardianService.getGuardianById(id);
            return new ResponseEntity<>(guardian, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        }
    }

    // delete an guardian
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGuardian(@PathVariable Long id) {
        try {
            guardianService.deleteGuardian(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("guardian deleted successfully", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete guardian"));
        }
    }

}