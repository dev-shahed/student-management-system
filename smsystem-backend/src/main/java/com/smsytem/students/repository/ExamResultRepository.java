package com.smsytem.students.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smsytem.students.entity.ExamResult;
import com.smsytem.students.entity.ExamResult.Grade;
import com.smsytem.students.entity.ExamResult.ResultStatus;

/**
 * Repository interface for ExamResult entity
 * Provides custom query methods for exam result management
 */
@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {

    /**
     * Find exam result by exam and student
     */
    Optional<ExamResult> findByExamExamIdAndStudentStudentID(Long examId, Long studentId);

    /**
     * Find all results for a specific exam
     */
    List<ExamResult> findByExamExamIdOrderByMarksObtainedDesc(Long examId);

    /**
     * Find all results for a specific student
     */
    List<ExamResult> findByStudentStudentIDOrderByExamExamDateDesc(Long studentId);

    /**
     * Find results by student and subject
     */
    @Query("SELECT er FROM ExamResult er WHERE er.student.studentID = :studentId " +
           "AND er.exam.subject.subjectID = :subjectId " +
           "ORDER BY er.exam.examDate DESC")
    List<ExamResult> findByStudentAndSubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    /**
     * Find results by class and exam
     */
    @Query("SELECT er FROM ExamResult er WHERE er.exam.examId = :examId " +
           "AND er.student.studentClass.classID = :classId " +
           "ORDER BY er.marksObtained DESC")
    List<ExamResult> findByClassAndExam(@Param("classId") Long classId, @Param("examId") Long examId);

    /**
     * Get class-wise results statistics
     */
    @Query("SELECT er.resultStatus, COUNT(er) FROM ExamResult er " +
           "WHERE er.exam.examId = :examId " +
           "GROUP BY er.resultStatus")
    List<Object[]> getResultStatsByExam(@Param("examId") Long examId);

    /**
     * Get grade distribution for an exam
     */
    @Query("SELECT er.grade, COUNT(er) FROM ExamResult er " +
           "WHERE er.exam.examId = :examId " +
           "GROUP BY er.grade " +
           "ORDER BY er.grade ASC")
    List<Object[]> getGradeDistributionByExam(@Param("examId") Long examId);

    /**
     * Find top performers in an exam
     */
    @Query("SELECT er FROM ExamResult er WHERE er.exam.examId = :examId " +
           "ORDER BY er.marksObtained DESC")
    List<ExamResult> findTopPerformersByExam(@Param("examId") Long examId);

    /**
     * Get student's average marks for a subject
     */
    @Query("SELECT AVG(er.marksObtained) FROM ExamResult er " +
           "WHERE er.student.studentID = :studentId " +
           "AND er.exam.subject.subjectID = :subjectId")
    Double getStudentAverageBySubject(@Param("studentId") Long studentId, @Param("subjectId") Long subjectId);

    /**
     * Get class average for an exam
     */
    @Query("SELECT AVG(er.marksObtained) FROM ExamResult er WHERE er.exam.examId = :examId")
    Double getClassAverageByExam(@Param("examId") Long examId);

    /**
     * Count results by grade
     */
    Long countByGrade(Grade grade);

    /**
     * Count results by status
     */
    Long countByResultStatus(ResultStatus status);

    /**
     * Find students who failed in an exam
     */
    @Query("SELECT er FROM ExamResult er WHERE er.exam.examId = :examId " +
           "AND er.resultStatus = 'FAIL' " +
           "ORDER BY er.marksObtained ASC")
    List<ExamResult> findFailedStudentsByExam(@Param("examId") Long examId);

    /**
     * Check if result exists for exam and student
     */
    boolean existsByExamExamIdAndStudentStudentID(Long examId, Long studentId);

    /**
     * Get student's overall performance
     */
    @Query("SELECT er.resultStatus, COUNT(er) FROM ExamResult er " +
           "WHERE er.student.studentID = :studentId " +
           "GROUP BY er.resultStatus")
    List<Object[]> getStudentOverallPerformance(@Param("studentId") Long studentId);
}
