package com.smsytem.students.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smsytem.students.entity.Attendance;
import com.smsytem.students.entity.Attendance.AttendanceStatus;

/**
 * Repository interface for Attendance entity
 * Provides custom query methods for attendance management
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Find attendance record for a specific student on a specific date
     */
    Optional<Attendance> findByStudentStudentIDAndAttendanceDate(Long studentId, LocalDate attendanceDate);

    /**
     * Find all attendance records for a student
     */
    List<Attendance> findByStudentStudentIDOrderByAttendanceDateDesc(Long studentId);

    /**
     * Find attendance records for a student within date range
     */
    @Query("SELECT a FROM Attendance a WHERE a.student.studentID = :studentId " +
           "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
           "ORDER BY a.attendanceDate DESC")
    List<Attendance> findByStudentAndDateRange(@Param("studentId") Long studentId, 
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    /**
     * Find all attendance records for a specific date
     */
    List<Attendance> findByAttendanceDateOrderByStudentStudentID(LocalDate attendanceDate);

    /**
     * Find attendance records for a class on a specific date
     */
    @Query("SELECT a FROM Attendance a WHERE a.student.studentClass.classID = :classId " +
           "AND a.attendanceDate = :attendanceDate " +
           "ORDER BY a.student.roll")
    List<Attendance> findByClassAndDate(@Param("classId") Long classId, 
                                       @Param("attendanceDate") LocalDate attendanceDate);

    /**
     * Count attendance by status for a student in date range
     */
    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.student.studentID = :studentId " +
           "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
           "AND a.status = :status")
    Long countByStudentAndStatusInDateRange(@Param("studentId") Long studentId,
                                          @Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate,
                                          @Param("status") AttendanceStatus status);

    /**
     * Get attendance statistics for a class
     */
    @Query("SELECT a.status, COUNT(a) FROM Attendance a WHERE a.student.studentClass.classID = :classId " +
           "AND a.attendanceDate BETWEEN :startDate AND :endDate " +
           "GROUP BY a.status")
    List<Object[]> getAttendanceStatsByClass(@Param("classId") Long classId,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    /**
     * Check if attendance exists for student on date
     */
    boolean existsByStudentStudentIDAndAttendanceDate(Long studentId, LocalDate attendanceDate);
}
