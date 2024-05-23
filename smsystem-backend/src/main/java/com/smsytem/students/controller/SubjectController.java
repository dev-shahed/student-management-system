package com.smsytem.students.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smsytem.students.dto.SubjectDTO;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.SubjectService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@CrossOrigin("*")
@RequestMapping("api/subjects")
public class SubjectController {
    private SubjectService subjectService;

    @PostMapping()
    public ResponseEntity<?> creatingClass(@RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectDTO createdSubject = subjectService.addSubject(subjectDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("New subject successfully", createdSubject));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to add your subject: " + e.getMessage()));
        }
    }

    // retrieve all Subjects..
    @GetMapping()
    public ResponseEntity<?> getAllSubjects() {
        try {
            List<SubjectDTO> subjects = subjectService.getAllSubjects();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("success", subjects));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve subjects: " + e.getMessage()));
        }
    }

    // // retrieve single guardian
    // @GetMapping("/{id}")
    // public ResponseEntity<?> ClassOrSectionById(@PathVariable Long id, HttpServletRequest request) {
    //     try {
    //         ClassDTO theClass = classOrSectionService.getClassById(id);
    //         return new ResponseEntity<>(theClass, HttpStatus.OK);
    //     } catch (ResourceNotFoundException ex) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    //     }
    // }

    // // delete an guardian
    // @DeleteMapping("/{id}")
    // public ResponseEntity<?> deleteClassOrSection(@PathVariable Long id) {
    //     try {
    //         classOrSectionService.deleteClass(id);
    //         return ResponseEntity.status(HttpStatus.OK)
    //                 .body(ApiResponse.success("class or section deleted successfully", null));
    //     } catch (ResourceNotFoundException ex) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getErrorResponse());
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    //                 .body(ApiResponse.error("Failed to delete class or section"));
    //     }
    // }

}