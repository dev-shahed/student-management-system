package com.smsytem.students.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing class timetable/schedule
 * Manages class schedules for different subjects and time periods
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "timetable",
       uniqueConstraints = @UniqueConstraint(columnNames = {"class_id", "day_of_week", "period_number"}))
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timetableId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassOrSection classSection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber; // 1st period, 2nd period, etc.

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "room_number")
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "period_type")
    private PeriodType periodType;

    @Column(name = "notes")
    private String notes;

    @Column(name = "is_active")
    private Boolean isActive = true;

    /**
     * Enum for different types of periods
     */
    public enum PeriodType {
        REGULAR_CLASS,
        LAB_SESSION,
        LIBRARY_PERIOD,
        SPORTS,
        BREAK,
        LUNCH_BREAK,
        ASSEMBLY,
        STUDY_HALL,
        EXTRA_CURRICULAR
    }
}
