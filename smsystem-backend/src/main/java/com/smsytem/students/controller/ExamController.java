package com.smsytem.students.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smsytem.students.dto.ExamDTO;
import com.smsytem.students.entity.Exam.ExamStatus;
import com.smsytem.students.entity.Exam.ExamType;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.AuthException;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.ExamService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * REST Controller for Exam management
 * Provides endpoints for creating, scheduling, and managing exams
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/exams")
@CrossOrigin("*")
@Api(tags = "Exam Management")
public class ExamController {

    private final ExamService examService;

    /**
     * Create a new exam
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping
    @ApiOperation(value = "Create a new exam")
    public ResponseEntity<?> createExam(@RequestBody ExamDTO examDTO) {
        try {
            ExamDTO createdExam = examService.createExam(examDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Exam created successfully", createdExam));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to create exam: " + e.getMessage()));
        }
    }

    /**
     * Update existing exam
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PutMapping("/{examId}")
    @ApiOperation(value = "Update existing exam")
    public ResponseEntity<?> updateExam(@PathVariable Long examId, @RequestBody ExamDTO examDTO) {
        try {
            ExamDTO updatedExam = examService.updateExam(examId, examDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam updated successfully", updatedExam));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update exam: " + e.getMessage()));
        }
    }

    /**
     * Get exam by ID
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT')")
    @GetMapping("/{examId}")
    @ApiOperation(value = "Get exam by ID")
    public ResponseEntity<?> getExamById(@PathVariable Long examId) {
        try {
            ExamDTO exam = examService.getExamById(examId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam retrieved successfully", exam));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exam: " + e.getMessage()));
        }
    }

    /**
     * Get all exams
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping
    @ApiOperation(value = "Get all exams")
    public ResponseEntity<?> getAllExams() {
        try {
            List<ExamDTO> exams = examService.getAllExams();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exams: " + e.getMessage()));
        }
    }

    /**
     * Get exams by class
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT')")
    @GetMapping("/class/{classId}")
    @ApiOperation(value = "Get exams by class")
    public ResponseEntity<?> getExamsByClass(@PathVariable Long classId) {
        try {
            List<ExamDTO> exams = examService.getExamsByClass(classId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Class exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve class exams: " + e.getMessage()));
        }
    }

    /**
     * Get exams by subject
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT')")
    @GetMapping("/subject/{subjectId}")
    @ApiOperation(value = "Get exams by subject")
    public ResponseEntity<?> getExamsBySubject(@PathVariable Long subjectId) {
        try {
            List<ExamDTO> exams = examService.getExamsBySubject(subjectId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Subject exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve subject exams: " + e.getMessage()));
        }
    }

    /**
     * Get upcoming exams for a class
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT')")
    @GetMapping("/upcoming/class/{classId}")
    @ApiOperation(value = "Get upcoming exams for a class")
    public ResponseEntity<?> getUpcomingExamsByClass(@PathVariable Long classId) {
        try {
            List<ExamDTO> exams = examService.getUpcomingExamsByClass(classId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Upcoming exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve upcoming exams: " + e.getMessage()));
        }
    }

    /**
     * Get today's exams
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/today")
    @ApiOperation(value = "Get today's exams")
    public ResponseEntity<?> getTodaysExams() {
        try {
            List<ExamDTO> exams = examService.getTodaysExams();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Today's exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve today's exams: " + e.getMessage()));
        }
    }

    /**
     * Get exams by date range
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/date-range")
    @ApiOperation(value = "Get exams by date range")
    public ResponseEntity<?> getExamsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<ExamDTO> exams = examService.getExamsByDateRange(startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exams: " + e.getMessage()));
        }
    }

    /**
     * Get exams by status
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/status/{status}")
    @ApiOperation(value = "Get exams by status")
    public ResponseEntity<?> getExamsByStatus(@PathVariable ExamStatus status) {
        try {
            List<ExamDTO> exams = examService.getExamsByStatus(status);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exams: " + e.getMessage()));
        }
    }

    /**
     * Get exams by type
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/type/{type}")
    @ApiOperation(value = "Get exams by type")
    public ResponseEntity<?> getExamsByType(@PathVariable ExamType type) {
        try {
            List<ExamDTO> exams = examService.getExamsByType(type);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exams retrieved successfully", exams));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exams: " + e.getMessage()));
        }
    }

    /**
     * Update exam status
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PutMapping("/{examId}/status")
    @ApiOperation(value = "Update exam status")
    public ResponseEntity<?> updateExamStatus(@PathVariable Long examId, @RequestParam ExamStatus status) {
        try {
            ExamDTO updatedExam = examService.updateExamStatus(examId, status);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam status updated successfully", updatedExam));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update exam status: " + e.getMessage()));
        }
    }

    /**
     * Get exam statistics
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/statistics")
    @ApiOperation(value = "Get exam statistics")
    public ResponseEntity<?> getExamStatistics() {
        try {
            Map<String, Object> stats = examService.getExamStatistics();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam statistics retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exam statistics: " + e.getMessage()));
        }
    }

    /**
     * Delete exam
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{examId}")
    @ApiOperation(value = "Delete exam")
    public ResponseEntity<?> deleteExam(@PathVariable Long examId) {
        try {
            examService.deleteExam(examId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete exam: " + e.getMessage()));
        }
    }
}
