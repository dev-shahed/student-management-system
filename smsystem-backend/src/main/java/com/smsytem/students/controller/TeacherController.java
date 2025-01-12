package com.smsytem.students.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsytem.students.dto.TeacherDTO;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.TeacherService;

import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("api/teachers")
public class TeacherController {
    private TeacherService teacherService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    public ResponseEntity<?> creatingTeacher(@RequestBody TeacherDTO teacherDTO) {
        try {
            TeacherDTO createdTeacher = teacherService.addTeacher(teacherDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("teacher created successfully", createdTeacher));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create Teacher: " + e.getMessage()));
        }
    }

    // retrieve all Teachers..
    @GetMapping()
    public ResponseEntity<?> getAllTeachers() {
        try {
            List<TeacherDTO> teachers = teacherService.allTeacher();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("fetched successfully", teachers));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve Teachers: " + e.getMessage()));
        }
    }

    // retrieve single Teacher.
    @GetMapping("/{id}")
    public ResponseEntity<?> teacherById(@PathVariable Long id, HttpServletRequest request) {
        try {
            TeacherDTO teacher = teacherService.getTeacherById(id);
            return new ResponseEntity<>(teacher, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        }
    }

    // update an existing Teacher.
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody TeacherDTO teacherDTO) {
        try {
            TeacherDTO updatedTeacher = teacherService.updateTeacher(id, teacherDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Teacher updated successfully", updatedTeacher));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update Teacher"));
        }
    }

    // delete an Teacher..
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Teacher deleted successfully", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete Teacher"));
        }
    }

}
