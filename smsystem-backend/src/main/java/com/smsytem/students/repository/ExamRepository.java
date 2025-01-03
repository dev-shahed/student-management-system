package com.smsytem.students.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smsytem.students.entity.Exam;
import com.smsytem.students.entity.Exam.ExamStatus;
import com.smsytem.students.entity.Exam.ExamType;

/**
 * Repository interface for Exam entity
 * Provides custom query methods for exam management
 */
@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    /**
     * Find exams by class ID
     */
    List<Exam> findByExamClassClassIDOrderByExamDateAsc(Long classId);

    /**
     * Find exams by subject ID
     */
    List<Exam> findBySubjectSubjectIDOrderByExamDateAsc(Long subjectId);

    /**
     * Find exams by class and subject
     */
    @Query("SELECT e FROM Exam e WHERE e.examClass.classID = :classId AND e.subject.subjectID = :subjectId ORDER BY e.examDate ASC")
    List<Exam> findByClassAndSubject(@Param("classId") Long classId, @Param("subjectId") Long subjectId);

    /**
     * Find exams by date range
     */
    @Query("SELECT e FROM Exam e WHERE e.examDate BETWEEN :startDate AND :endDate ORDER BY e.examDate ASC")
    List<Exam> findByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * Find exams by status
     */
    List<Exam> findByStatusOrderByExamDateAsc(ExamStatus status);

    /**
     * Find exams by type
     */
    List<Exam> findByExamTypeOrderByExamDateAsc(ExamType examType);

    /**
     * Find upcoming exams for a class
     */
    @Query("SELECT e FROM Exam e WHERE e.examClass.classID = :classId AND e.examDate >= :currentDate ORDER BY e.examDate ASC")
    List<Exam> findUpcomingExamsByClass(@Param("classId") Long classId, @Param("currentDate") LocalDate currentDate);

    /**
     * Find exams scheduled for today
     */
    @Query("SELECT e FROM Exam e WHERE e.examDate = :today ORDER BY e.startTime ASC")
    List<Exam> findTodaysExams(@Param("today") LocalDate today);

    /**
     * Find exams created by a specific user
     */
    List<Exam> findByCreatedByIdOrderByExamDateDesc(Long createdBy);

    /**
     * Find exams by class and date range
     */
    @Query("SELECT e FROM Exam e WHERE e.examClass.classID = :classId AND e.examDate BETWEEN :startDate AND :endDate ORDER BY e.examDate ASC")
    List<Exam> findByClassAndDateRange(@Param("classId") Long classId, 
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    /**
     * Count exams by status
     */
    Long countByStatus(ExamStatus status);

    /**
     * Check if exam exists for class, subject and date
     */
    boolean existsByExamClassClassIDAndSubjectSubjectIDAndExamDate(Long classId, Long subjectId, LocalDate examDate);
}
