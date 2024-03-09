package com.smsytem.students.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsytem.students.dto.StudentDTO;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.StudentService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/students")
public class StudentController {
    private StudentService studentService;

    @PostMapping()
    public ResponseEntity<?> creatingStudent(@RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO createdStudent = studentService.createStudent(studentDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Student created successfully", createdStudent));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create student: " + e.getMessage()));
        }
    }

    // retrieve all students..
    @GetMapping()
    public ResponseEntity<?> getAllStudents() {
        try {
            List<StudentDTO> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve students: " + e.getMessage()));
        }
    }

    // retrieve single student.
    @GetMapping("/{id}")
    public ResponseEntity<?> studentById(@PathVariable Long id, HttpServletRequest request) {
        try {
            StudentDTO student = studentService.getStudentById(id);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        }
    }

    // update an existing student.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        try {
            StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student updated successfully", updatedStudent));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update student"));
        }
    }

    // delete an student..
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student deleted successfully", null));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete student"));
        }
    }

}
