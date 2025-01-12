package com.smsytem.students.controller;

import java.util.List;
import java.util.Map;

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

import com.smsytem.students.dto.ExamResultDTO;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.AuthException;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.ExamResultService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * REST Controller for ExamResult management
 * Provides endpoints for managing exam results and grades
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/exam-results")
@CrossOrigin("*")
@Api(tags = "Exam Result Management")
public class ExamResultController {

    private final ExamResultService examResultService;

    /**
     * Add or save exam result
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping
    @ApiOperation(value = "Add exam result")
    public ResponseEntity<?> saveExamResult(@RequestBody ExamResultDTO examResultDTO) {
        try {
            ExamResultDTO savedResult = examResultService.saveExamResult(examResultDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Exam result saved successfully", savedResult));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to save exam result: " + e.getMessage()));
        }
    }

    /**
     * Update existing exam result
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PutMapping("/{resultId}")
    @ApiOperation(value = "Update exam result")
    public ResponseEntity<?> updateExamResult(@PathVariable Long resultId, @RequestBody ExamResultDTO examResultDTO) {
        try {
            ExamResultDTO updatedResult = examResultService.updateExamResult(resultId, examResultDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam result updated successfully", updatedResult));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update exam result: " + e.getMessage()));
        }
    }

    /**
     * Get exam result by ID
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/{resultId}")
    @ApiOperation(value = "Get exam result by ID")
    public ResponseEntity<?> getExamResultById(@PathVariable Long resultId) {
        try {
            ExamResultDTO result = examResultService.getExamResultById(resultId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam result retrieved successfully", result));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exam result: " + e.getMessage()));
        }
    }

    /**
     * Get all results for a specific exam
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/exam/{examId}")
    @ApiOperation(value = "Get all results for an exam")
    public ResponseEntity<?> getResultsByExam(@PathVariable Long examId) {
        try {
            List<ExamResultDTO> results = examResultService.getResultsByExam(examId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam results retrieved successfully", results));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exam results: " + e.getMessage()));
        }
    }

    /**
     * Get all results for a specific student
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/student/{studentId}")
    @ApiOperation(value = "Get all results for a student")
    public ResponseEntity<?> getResultsByStudent(@PathVariable Long studentId) {
        try {
            List<ExamResultDTO> results = examResultService.getResultsByStudent(studentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student results retrieved successfully", results));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve student results: " + e.getMessage()));
        }
    }

    /**
     * Get results by student and subject
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/student/{studentId}/subject/{subjectId}")
    @ApiOperation(value = "Get results by student and subject")
    public ResponseEntity<?> getResultsByStudentAndSubject(@PathVariable Long studentId, @PathVariable Long subjectId) {
        try {
            List<ExamResultDTO> results = examResultService.getResultsByStudentAndSubject(studentId, subjectId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Results retrieved successfully", results));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve results: " + e.getMessage()));
        }
    }

    /**
     * Get top performers for an exam
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/exam/{examId}/top-performers")
    @ApiOperation(value = "Get top performers for an exam")
    public ResponseEntity<?> getTopPerformersByExam(@PathVariable Long examId, @RequestParam(defaultValue = "10") Integer limit) {
        try {
            List<ExamResultDTO> topPerformers = examResultService.getTopPerformersByExam(examId, limit);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Top performers retrieved successfully", topPerformers));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve top performers: " + e.getMessage()));
        }
    }

    /**
     * Get failed students for an exam
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/exam/{examId}/failed-students")
    @ApiOperation(value = "Get failed students for an exam")
    public ResponseEntity<?> getFailedStudentsByExam(@PathVariable Long examId) {
        try {
            List<ExamResultDTO> failedStudents = examResultService.getFailedStudentsByExam(examId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Failed students retrieved successfully", failedStudents));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve failed students: " + e.getMessage()));
        }
    }

    /**
     * Get exam result statistics
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/exam/{examId}/statistics")
    @ApiOperation(value = "Get exam result statistics")
    public ResponseEntity<?> getExamResultStats(@PathVariable Long examId) {
        try {
            Map<String, Object> stats = examResultService.getExamResultStats(examId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam statistics retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve exam statistics: " + e.getMessage()));
        }
    }

    /**
     * Get grade distribution for an exam
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/exam/{examId}/grade-distribution")
    @ApiOperation(value = "Get grade distribution for an exam")
    public ResponseEntity<?> getGradeDistribution(@PathVariable Long examId) {
        try {
            Map<String, Object> distribution = examResultService.getGradeDistribution(examId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Grade distribution retrieved successfully", distribution));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve grade distribution: " + e.getMessage()));
        }
    }

    /**
     * Get student's overall performance
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/student/{studentId}/performance")
    @ApiOperation(value = "Get student's overall performance")
    public ResponseEntity<?> getStudentOverallPerformance(@PathVariable Long studentId) {
        try {
            Map<String, Object> performance = examResultService.getStudentOverallPerformance(studentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student performance retrieved successfully", performance));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve student performance: " + e.getMessage()));
        }
    }

    /**
     * Generate report card for student
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/student/{studentId}/report-card")
    @ApiOperation(value = "Generate report card for student")
    public ResponseEntity<?> generateReportCard(@PathVariable Long studentId, @RequestParam Long classId) {
        try {
            Map<String, Object> reportCard = examResultService.generateReportCard(studentId, classId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Report card generated successfully", reportCard));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to generate report card: " + e.getMessage()));
        }
    }

    /**
     * Bulk upload exam results
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping("/exam/{examId}/bulk-upload")
    @ApiOperation(value = "Bulk upload exam results")
    public ResponseEntity<?> bulkUploadResults(@PathVariable Long examId, @RequestBody List<ExamResultDTO> results) {
        try {
            List<ExamResultDTO> uploadedResults = examResultService.bulkUploadResults(examId, results);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Exam results uploaded successfully", uploadedResults));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to upload exam results: " + e.getMessage()));
        }
    }

    /**
     * Delete exam result
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{resultId}")
    @ApiOperation(value = "Delete exam result")
    public ResponseEntity<?> deleteExamResult(@PathVariable Long resultId) {
        try {
            examResultService.deleteExamResult(resultId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Exam result deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete exam result: " + e.getMessage()));
        }
    }
}
