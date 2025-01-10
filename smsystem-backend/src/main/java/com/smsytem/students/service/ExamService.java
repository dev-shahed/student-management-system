package com.smsytem.students.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.smsytem.students.dto.ExamDTO;
import com.smsytem.students.entity.Exam.ExamStatus;
import com.smsytem.students.entity.Exam.ExamType;

/**
 * Service interface for Exam management
 * Defines operations for exam creation, scheduling, and management
 */
public interface ExamService {

    /**
     * Create a new exam
     */
    ExamDTO createExam(ExamDTO examDTO);

    /**
     * Update existing exam
     */
    ExamDTO updateExam(Long examId, ExamDTO examDTO);

    /**
     * Get exam by ID
     */
    ExamDTO getExamById(Long examId);

    /**
     * Get all exams
     */
    List<ExamDTO> getAllExams();

    /**
     * Get exams by class
     */
    List<ExamDTO> getExamsByClass(Long classId);

    /**
     * Get exams by subject
     */
    List<ExamDTO> getExamsBySubject(Long subjectId);

    /**
     * Get exams by class and subject
     */
    List<ExamDTO> getExamsByClassAndSubject(Long classId, Long subjectId);

    /**
     * Get exams by date range
     */
    List<ExamDTO> getExamsByDateRange(LocalDate startDate, LocalDate endDate);

    /**
     * Get exams by status
     */
    List<ExamDTO> getExamsByStatus(ExamStatus status);

    /**
     * Get exams by type
     */
    List<ExamDTO> getExamsByType(ExamType examType);

    /**
     * Get upcoming exams for a class
     */
    List<ExamDTO> getUpcomingExamsByClass(Long classId);

    /**
     * Get today's exams
     */
    List<ExamDTO> getTodaysExams();

    /**
     * Update exam status
     */
    ExamDTO updateExamStatus(Long examId, ExamStatus status);

    /**
     * Delete exam
     */
    void deleteExam(Long examId);

    /**
     * Get exam statistics
     */
    Map<String, Object> getExamStatistics();

    /**
     * Get exams created by a user
     */
    List<ExamDTO> getExamsByCreator(Long creatorId);
}
