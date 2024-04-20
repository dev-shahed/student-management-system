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

import com.smsytem.students.dto.ClassDTO;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.ClassOrSectionService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("api/classes")
public class ClassOrSectionController {
    private ClassOrSectionService classOrSectionService;

    @PostMapping()
    public ResponseEntity<?> creatingClass(@RequestBody ClassDTO classDTO) {
        try {
            ClassDTO createdClass = classOrSectionService.addClass(classDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("class or section created successfully", createdClass));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create class or section: " + e.getMessage()));
        }
    }

    // retrieve single guardian
    @GetMapping("/{id}")
    public ResponseEntity<?> ClassOrSectionById(@PathVariable Long id, HttpServletRequest request) {
        try {
            ClassDTO theClass = classOrSectionService.getClassById(id);
            return new ResponseEntity<>(theClass, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        }
    }

    // delete an guardian
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClassOrSection(@PathVariable Long id) {
        try {
            classOrSectionService.deleteClass(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("class or section deleted successfully", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete class or section"));
        }
    }

}