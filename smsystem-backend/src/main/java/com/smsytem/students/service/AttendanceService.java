package com.smsytem.students.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.smsytem.students.dto.AttendanceDTO;
import com.smsytem.students.entity.Attendance.AttendanceStatus;

/**
 * Service interface for Attendance management
 * Defines operations for attendance tracking and reporting
 */
public interface AttendanceService {

    /**
     * Mark attendance for a student
     */
    AttendanceDTO markAttendance(AttendanceDTO attendanceDTO);

    /**
     * Update existing attendance record
     */
    AttendanceDTO updateAttendance(Long attendanceId, AttendanceDTO attendanceDTO);

    /**
     * Get attendance record by ID
     */
    AttendanceDTO getAttendanceById(Long attendanceId);

    /**
     * Get all attendance records for a student
     */
    List<AttendanceDTO> getStudentAttendance(Long studentId);

    /**
     * Get attendance records for a student within date range
     */
    List<AttendanceDTO> getStudentAttendanceByDateRange(Long studentId, LocalDate startDate, LocalDate endDate);

    /**
     * Get all attendance records for a specific date
     */
    List<AttendanceDTO> getAttendanceByDate(LocalDate attendanceDate);

    /**
     * Get attendance records for a class on a specific date
     */
    List<AttendanceDTO> getClassAttendanceByDate(Long classId, LocalDate attendanceDate);

    /**
     * Mark attendance for entire class
     */
    List<AttendanceDTO> markClassAttendance(Long classId, LocalDate attendanceDate, List<AttendanceDTO> attendanceList);

    /**
     * Get attendance statistics for a student
     */
    Map<String, Object> getStudentAttendanceStats(Long studentId, LocalDate startDate, LocalDate endDate);

    /**
     * Get attendance statistics for a class
     */
    Map<String, Object> getClassAttendanceStats(Long classId, LocalDate startDate, LocalDate endDate);

    /**
     * Delete attendance record
     */
    void deleteAttendance(Long attendanceId);

    /**
     * Get attendance percentage for a student
     */
    Double getStudentAttendancePercentage(Long studentId, LocalDate startDate, LocalDate endDate);
}
