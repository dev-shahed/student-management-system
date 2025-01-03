package com.smsytem.students.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.smsytem.students.entity.Exam.ExamStatus;
import com.smsytem.students.entity.Exam.ExamType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Exam
 * Used for API requests and responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {

    private Long examId;
    private String examName;
    private String description;
    private Long subjectId;
    private String subjectName; // For display
    private Long classId;
    private String className; // For display
    private LocalDate examDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer durationMinutes;
    private Integer totalMarks;
    private Integer passingMarks;
    private ExamType examType;
    private ExamStatus status;
    private Long createdBy;
    private String createdByName; // For display
    private String instructions;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
