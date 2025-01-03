package com.smsytem.students.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.smsytem.students.entity.Attendance.AttendanceStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Attendance
 * Used for API requests and responses
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    
    private Long attendanceId;
    private Long studentId;
    private String studentName; // For display purposes
    private String className; // For display purposes
    private LocalDate attendanceDate;
    private AttendanceStatus status;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String remarks;
    private Long markedBy; // User ID who marked attendance
    private String markedByName; // Name of user who marked attendance
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
