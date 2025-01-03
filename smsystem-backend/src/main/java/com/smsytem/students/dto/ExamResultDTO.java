package com.smsytem.students.dto;

import java.time.LocalDate;

import com.smsytem.students.entity.ExamResult.Grade;
import com.smsytem.students.entity.ExamResult.ResultStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for ExamResult
 * Used for API requests and responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultDTO {

    private Long resultId;
    private Long examId;
    private String examName; // For display
    private String subjectName; // For display
    private Long studentId;
    private String studentName; // For display
    private String studentRoll; // For display
    private Double marksObtained;
    private Double totalMarks;
    private Double percentage;
    private Grade grade;
    private ResultStatus resultStatus;
    private String remarks;
    private Boolean isAbsent;
    private Long evaluatedBy;
    private String evaluatedByName; // For display
    private LocalDate evaluationDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
