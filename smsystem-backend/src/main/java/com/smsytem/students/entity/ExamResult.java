package com.smsytem.students.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing exam results for students
 * Tracks marks, grades, and performance for each exam
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "exam_results",
       uniqueConstraints = @UniqueConstraint(columnNames = {"exam_id", "student_id"}))
public class ExamResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id", nullable = false)
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "marks_obtained", nullable = false)
    private Double marksObtained;

    @Column(name = "total_marks", nullable = false)
    private Double totalMarks;

    @Transient
    private Double percentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    private Grade grade;

    @Enumerated(EnumType.STRING)
    @Column(name = "result_status")
    private ResultStatus resultStatus;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "is_absent")
    private Boolean isAbsent = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluated_by")
    private User evaluatedBy; // Teacher who evaluated

    @Column(name = "evaluation_date")
    private LocalDate evaluationDate;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;

    /**
     * Calculate percentage and determine pass/fail status
     */
    public void calculateResult() {
        if (totalMarks != null && totalMarks > 0) {
            this.percentage = (marksObtained / totalMarks) * 100;
            
            // Determine grade based on percentage
            if (percentage >= 90) {
                this.grade = Grade.A_PLUS;
            } else if (percentage >= 80) {
                this.grade = Grade.A;
            } else if (percentage >= 70) {
                this.grade = Grade.B_PLUS;
            } else if (percentage >= 60) {
                this.grade = Grade.B;
            } else if (percentage >= 50) {
                this.grade = Grade.C_PLUS;
            } else if (percentage >= 40) {
                this.grade = Grade.C;
            } else if (percentage >= 33) {
                this.grade = Grade.D;
            } else {
                this.grade = Grade.F;
            }

            // Determine result status
            Double passingPercentage = exam != null && exam.getPassingMarks() != null 
                ? (exam.getPassingMarks().doubleValue() / exam.getTotalMarks().doubleValue()) * 100 
                : 33.0;
            
            this.resultStatus = percentage >= passingPercentage ? ResultStatus.PASS : ResultStatus.FAIL;
        }
    }

    /**
     * Enum for academic grades
     */
    public enum Grade {
        A_PLUS("A+", 90.0),
        A("A", 80.0),
        B_PLUS("B+", 70.0),
        B("B", 60.0),
        C_PLUS("C+", 50.0),
        C("C", 40.0),
        D("D", 33.0),
        F("F", 0.0);

        private final String displayName;
        private final Double minPercentage;

        Grade(String displayName, Double minPercentage) {
            this.displayName = displayName;
            this.minPercentage = minPercentage;
        }

        public String getDisplayName() {
            return displayName;
        }

        public Double getMinPercentage() {
            return minPercentage;
        }
    }

    /**
     * Enum for result status
     */
    public enum ResultStatus {
        PASS,
        FAIL,
        PENDING,
        WITHHELD
    }
}
