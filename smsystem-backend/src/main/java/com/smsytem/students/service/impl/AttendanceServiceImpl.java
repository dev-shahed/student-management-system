package com.smsytem.students.service.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smsytem.students.dto.AttendanceDTO;
import com.smsytem.students.entity.Attendance;
import com.smsytem.students.entity.Attendance.AttendanceStatus;
import com.smsytem.students.entity.Student;
import com.smsytem.students.entity.User;
import com.smsytem.students.exception.AuthException;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.AttendanceRepository;
import com.smsytem.students.repository.StudentRepository;
import com.smsytem.students.repository.UserRepository;
import com.smsytem.students.service.AttendanceService;

import lombok.AllArgsConstructor;

/**
 * Service implementation for Attendance management
 * Handles attendance tracking, statistics, and business logic
 */
@AllArgsConstructor
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public AttendanceDTO markAttendance(AttendanceDTO attendanceDTO) {
        // Check if attendance already exists for the student on this date
        if (attendanceRepository.existsByStudentStudentIDAndAttendanceDate(
                attendanceDTO.getStudentId(), attendanceDTO.getAttendanceDate())) {
            throw new AuthException("Attendance already marked for this student on " + attendanceDTO.getAttendanceDate());
        }

        // Get student entity
        Student student = studentRepository.findById(attendanceDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + attendanceDTO.getStudentId()));

        // Get current user (who is marking attendance)
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User markedBy = userRepository.findByUsernameOrEmail(currentUsername, currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + currentUsername));

        // Create attendance entity
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setAttendanceDate(attendanceDTO.getAttendanceDate());
        attendance.setStatus(attendanceDTO.getStatus());
        attendance.setCheckInTime(attendanceDTO.getCheckInTime());
        attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        attendance.setRemarks(attendanceDTO.getRemarks());
        attendance.setMarkedBy(markedBy);
        attendance.setCreatedAt(LocalDate.now());
        attendance.setUpdatedAt(LocalDate.now());

        Attendance savedAttendance = attendanceRepository.save(attendance);
        return mapToDTO(savedAttendance);
    }

    @Override
    public AttendanceDTO updateAttendance(Long attendanceId, AttendanceDTO attendanceDTO) {
        Attendance existingAttendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with ID: " + attendanceId));

        // Update fields
        existingAttendance.setStatus(attendanceDTO.getStatus());
        existingAttendance.setCheckInTime(attendanceDTO.getCheckInTime());
        existingAttendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
        existingAttendance.setRemarks(attendanceDTO.getRemarks());
        existingAttendance.setUpdatedAt(LocalDate.now());

        Attendance updatedAttendance = attendanceRepository.save(existingAttendance);
        return mapToDTO(updatedAttendance);
    }

    @Override
    public AttendanceDTO getAttendanceById(Long attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found with ID: " + attendanceId));
        return mapToDTO(attendance);
    }

    @Override
    public List<AttendanceDTO> getStudentAttendance(Long studentId) {
        // Verify student exists
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        List<Attendance> attendanceList = attendanceRepository.findByStudentStudentIDOrderByAttendanceDateDesc(studentId);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getStudentAttendanceByDateRange(Long studentId, LocalDate startDate, LocalDate endDate) {
        // Verify student exists
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        List<Attendance> attendanceList = attendanceRepository.findByStudentAndDateRange(studentId, startDate, endDate);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getAttendanceByDate(LocalDate attendanceDate) {
        List<Attendance> attendanceList = attendanceRepository.findByAttendanceDateOrderByStudentStudentID(attendanceDate);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> getClassAttendanceByDate(Long classId, LocalDate attendanceDate) {
        List<Attendance> attendanceList = attendanceRepository.findByClassAndDate(classId, attendanceDate);
        return attendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttendanceDTO> markClassAttendance(Long classId, LocalDate attendanceDate, List<AttendanceDTO> attendanceList) {
        // Get current user
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User markedBy = userRepository.findByUsernameOrEmail(currentUsername, currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + currentUsername));

        List<Attendance> savedAttendanceList = attendanceList.stream()
                .map(attendanceDTO -> {
                    // Check if attendance already exists
                    if (attendanceRepository.existsByStudentStudentIDAndAttendanceDate(
                            attendanceDTO.getStudentId(), attendanceDate)) {
                        throw new AuthException("Attendance already marked for student ID: " + attendanceDTO.getStudentId() + " on " + attendanceDate);
                    }

                    Student student = studentRepository.findById(attendanceDTO.getStudentId())
                            .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + attendanceDTO.getStudentId()));

                    Attendance attendance = new Attendance();
                    attendance.setStudent(student);
                    attendance.setAttendanceDate(attendanceDate);
                    attendance.setStatus(attendanceDTO.getStatus());
                    attendance.setCheckInTime(attendanceDTO.getCheckInTime());
                    attendance.setCheckOutTime(attendanceDTO.getCheckOutTime());
                    attendance.setRemarks(attendanceDTO.getRemarks());
                    attendance.setMarkedBy(markedBy);
                    attendance.setCreatedAt(LocalDate.now());
                    attendance.setUpdatedAt(LocalDate.now());

                    return attendanceRepository.save(attendance);
                })
                .collect(Collectors.toList());

        return savedAttendanceList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getStudentAttendanceStats(Long studentId, LocalDate startDate, LocalDate endDate) {
        // Verify student exists
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }

        Map<String, Object> stats = new HashMap<>();
        
        Long presentDays = attendanceRepository.countByStudentAndStatusInDateRange(studentId, startDate, endDate, AttendanceStatus.PRESENT);
        Long absentDays = attendanceRepository.countByStudentAndStatusInDateRange(studentId, startDate, endDate, AttendanceStatus.ABSENT);
        Long lateDays = attendanceRepository.countByStudentAndStatusInDateRange(studentId, startDate, endDate, AttendanceStatus.LATE);
        Long excusedDays = attendanceRepository.countByStudentAndStatusInDateRange(studentId, startDate, endDate, AttendanceStatus.EXCUSED);
        Long halfDays = attendanceRepository.countByStudentAndStatusInDateRange(studentId, startDate, endDate, AttendanceStatus.HALF_DAY);
        
        Long totalDays = presentDays + absentDays + lateDays + excusedDays + halfDays;
        Double attendancePercentage = totalDays > 0 ? (presentDays.doubleValue() / totalDays.doubleValue()) * 100 : 0.0;

        stats.put("presentDays", presentDays);
        stats.put("absentDays", absentDays);
        stats.put("lateDays", lateDays);
        stats.put("excusedDays", excusedDays);
        stats.put("halfDays", halfDays);
        stats.put("totalDays", totalDays);
        stats.put("attendancePercentage", Math.round(attendancePercentage * 100.0) / 100.0);

        return stats;
    }

    @Override
    public Map<String, Object> getClassAttendanceStats(Long classId, LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = attendanceRepository.getAttendanceStatsByClass(classId, startDate, endDate);
        
        Map<String, Object> stats = new HashMap<>();
        Long totalRecords = 0L;
        
        for (Object[] result : results) {
            AttendanceStatus status = (AttendanceStatus) result[0];
            Long count = (Long) result[1];
            stats.put(status.name().toLowerCase() + "Count", count);
            totalRecords += count;
        }
        
        stats.put("totalRecords", totalRecords);
        return stats;
    }

    @Override
    public void deleteAttendance(Long attendanceId) {
        if (!attendanceRepository.existsById(attendanceId)) {
            throw new ResourceNotFoundException("Attendance record not found with ID: " + attendanceId);
        }
        attendanceRepository.deleteById(attendanceId);
    }

    @Override
    public Double getStudentAttendancePercentage(Long studentId, LocalDate startDate, LocalDate endDate) {
        Map<String, Object> stats = getStudentAttendanceStats(studentId, startDate, endDate);
        return (Double) stats.get("attendancePercentage");
    }

    /**
     * Helper method to map Attendance entity to DTO
     */
    private AttendanceDTO mapToDTO(Attendance attendance) {
        AttendanceDTO dto = modelMapper.map(attendance, AttendanceDTO.class);
        dto.setStudentId(attendance.getStudent().getStudentID());
        dto.setStudentName(attendance.getStudent().getFirstName() + " " + attendance.getStudent().getLastName());
        dto.setClassName(attendance.getStudent().getStudentClass().getClassName());
        dto.setMarkedBy(attendance.getMarkedBy().getId());
        dto.setMarkedByName(attendance.getMarkedBy().getName());
        return dto;
    }
}
