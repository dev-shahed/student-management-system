package com.smsytem.students.service;

import java.util.List;
import java.util.Map;

import com.smsytem.students.dto.ExamResultDTO;

/**
 * Service interface for ExamResult management
 * Defines operations for managing exam results and grades
 */
public interface ExamResultService {

    /**
     * Add or update exam result
     */
    ExamResultDTO saveExamResult(ExamResultDTO examResultDTO);

    /**
     * Update existing exam result
     */
    ExamResultDTO updateExamResult(Long resultId, ExamResultDTO examResultDTO);

    /**
     * Get exam result by ID
     */
    ExamResultDTO getExamResultById(Long resultId);

    /**
     * Get exam result by exam and student
     */
    ExamResultDTO getExamResultByExamAndStudent(Long examId, Long studentId);

    /**
     * Get all results for a specific exam
     */
    List<ExamResultDTO> getResultsByExam(Long examId);

    /**
     * Get all results for a specific student
     */
    List<ExamResultDTO> getResultsByStudent(Long studentId);

    /**
     * Get results by student and subject
     */
    List<ExamResultDTO> getResultsByStudentAndSubject(Long studentId, Long subjectId);

    /**
     * Get results by class and exam
     */
    List<ExamResultDTO> getResultsByClassAndExam(Long classId, Long examId);

    /**
     * Get top performers for an exam
     */
    List<ExamResultDTO> getTopPerformersByExam(Long examId, Integer limit);

    /**
     * Get students who failed in an exam
     */
    List<ExamResultDTO> getFailedStudentsByExam(Long examId);

    /**
     * Get exam result statistics
     */
    Map<String, Object> getExamResultStats(Long examId);

    /**
     * Get grade distribution for an exam
     */
    Map<String, Object> getGradeDistribution(Long examId);

    /**
     * Get student's overall performance
     */
    Map<String, Object> getStudentOverallPerformance(Long studentId);

    /**
     * Get class average for an exam
     */
    Double getClassAverageByExam(Long examId);

    /**
     * Get student's average marks for a subject
     */
    Double getStudentAverageBySubject(Long studentId, Long subjectId);

    /**
     * Bulk upload exam results
     */
    List<ExamResultDTO> bulkUploadResults(Long examId, List<ExamResultDTO> results);

    /**
     * Delete exam result
     */
    void deleteExamResult(Long resultId);

    /**
     * Generate report card for student
     */
    Map<String, Object> generateReportCard(Long studentId, Long classId);
}
