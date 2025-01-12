package com.smsytem.students.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smsytem.students.dto.AttendanceDTO;
import com.smsytem.students.exception.ApiResponse;
import com.smsytem.students.exception.AuthException;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.service.AttendanceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * REST Controller for Attendance management
 * Provides endpoints for marking, updating, and retrieving attendance records
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/attendance")
@CrossOrigin("*")
@Api(tags = "Attendance Management")
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Mark attendance for a student
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping("/mark")
    @ApiOperation(value = "Mark attendance for a student")
    public ResponseEntity<?> markAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        try {
            AttendanceDTO markedAttendance = attendanceService.markAttendance(attendanceDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Attendance marked successfully", markedAttendance));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to mark attendance: " + e.getMessage()));
        }
    }

    /**
     * Mark attendance for entire class
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PostMapping("/mark-class/{classId}")
    @ApiOperation(value = "Mark attendance for entire class")
    public ResponseEntity<?> markClassAttendance(
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate attendanceDate,
            @RequestBody List<AttendanceDTO> attendanceList) {
        try {
            List<AttendanceDTO> markedAttendance = attendanceService.markClassAttendance(classId, attendanceDate, attendanceList);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Class attendance marked successfully", markedAttendance));
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to mark class attendance: " + e.getMessage()));
        }
    }

    /**
     * Update existing attendance record
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @PutMapping("/{attendanceId}")
    @ApiOperation(value = "Update attendance record")
    public ResponseEntity<?> updateAttendance(@PathVariable Long attendanceId, @RequestBody AttendanceDTO attendanceDTO) {
        try {
            AttendanceDTO updatedAttendance = attendanceService.updateAttendance(attendanceId, attendanceDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Attendance updated successfully", updatedAttendance));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to update attendance: " + e.getMessage()));
        }
    }

    /**
     * Get attendance record by ID
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT')")
    @GetMapping("/{attendanceId}")
    @ApiOperation(value = "Get attendance record by ID")
    public ResponseEntity<?> getAttendanceById(@PathVariable Long attendanceId) {
        try {
            AttendanceDTO attendance = attendanceService.getAttendanceById(attendanceId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Attendance retrieved successfully", attendance));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve attendance: " + e.getMessage()));
        }
    }

    /**
     * Get all attendance records for a student
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/student/{studentId}")
    @ApiOperation(value = "Get all attendance records for a student")
    public ResponseEntity<?> getStudentAttendance(@PathVariable Long studentId) {
        try {
            List<AttendanceDTO> attendanceList = attendanceService.getStudentAttendance(studentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student attendance retrieved successfully", attendanceList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve student attendance: " + e.getMessage()));
        }
    }

    /**
     * Get student attendance by date range
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/student/{studentId}/date-range")
    @ApiOperation(value = "Get student attendance by date range")
    public ResponseEntity<?> getStudentAttendanceByDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            List<AttendanceDTO> attendanceList = attendanceService.getStudentAttendanceByDateRange(studentId, startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student attendance retrieved successfully", attendanceList));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve student attendance: " + e.getMessage()));
        }
    }

    /**
     * Get attendance for a specific date
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/date/{attendanceDate}")
    @ApiOperation(value = "Get attendance for a specific date")
    public ResponseEntity<?> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate attendanceDate) {
        try {
            List<AttendanceDTO> attendanceList = attendanceService.getAttendanceByDate(attendanceDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Attendance retrieved successfully", attendanceList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve attendance: " + e.getMessage()));
        }
    }

    /**
     * Get class attendance for a specific date
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/class/{classId}/date/{attendanceDate}")
    @ApiOperation(value = "Get class attendance for a specific date")
    public ResponseEntity<?> getClassAttendanceByDate(
            @PathVariable Long classId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate attendanceDate) {
        try {
            List<AttendanceDTO> attendanceList = attendanceService.getClassAttendanceByDate(classId, attendanceDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Class attendance retrieved successfully", attendanceList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve class attendance: " + e.getMessage()));
        }
    }

    /**
     * Get attendance statistics for a student
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/stats/student/{studentId}")
    @ApiOperation(value = "Get attendance statistics for a student")
    public ResponseEntity<?> getStudentAttendanceStats(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            Map<String, Object> stats = attendanceService.getStudentAttendanceStats(studentId, startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student attendance statistics retrieved successfully", stats));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve attendance statistics: " + e.getMessage()));
        }
    }

    /**
     * Get attendance statistics for a class
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN')")
    @GetMapping("/stats/class/{classId}")
    @ApiOperation(value = "Get attendance statistics for a class")
    public ResponseEntity<?> getClassAttendanceStats(
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            Map<String, Object> stats = attendanceService.getClassAttendanceStats(classId, startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Class attendance statistics retrieved successfully", stats));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve class attendance statistics: " + e.getMessage()));
        }
    }

    /**
     * Get attendance percentage for a student
     */
    @PreAuthorize("hasAnyRole('TEACHER', 'ADMIN', 'STUDENT', 'PARENT')")
    @GetMapping("/percentage/student/{studentId}")
    @ApiOperation(value = "Get attendance percentage for a student")
    public ResponseEntity<?> getStudentAttendancePercentage(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            Double percentage = attendanceService.getStudentAttendancePercentage(studentId, startDate, endDate);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Student attendance percentage retrieved successfully", percentage));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to retrieve attendance percentage: " + e.getMessage()));
        }
    }

    /**
     * Delete attendance record
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{attendanceId}")
    @ApiOperation(value = "Delete attendance record")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long attendanceId) {
        try {
            attendanceService.deleteAttendance(attendanceId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success("Attendance deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to delete attendance: " + e.getMessage()));
        }
    }
}
