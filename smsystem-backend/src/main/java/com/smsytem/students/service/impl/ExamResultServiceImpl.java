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

import com.smsytem.students.dto.ExamResultDTO;
import com.smsytem.students.entity.Exam;
import com.smsytem.students.entity.ExamResult;
import com.smsytem.students.entity.Student;
import com.smsytem.students.entity.User;
import com.smsytem.students.exception.AuthException;
import com.smsytem.students.exception.ResourceNotFoundException;
import com.smsytem.students.repository.ExamRepository;
import com.smsytem.students.repository.ExamResultRepository;
import com.smsytem.students.repository.StudentRepository;
import com.smsytem.students.repository.UserRepository;
import com.smsytem.students.service.ExamResultService;

import lombok.AllArgsConstructor;

/**
 * Service implementation for ExamResult management
 * Handles exam results, grades, and performance analysis
 */
@AllArgsConstructor
@Service
@Transactional
public class ExamResultServiceImpl implements ExamResultService {

    private final ExamResultRepository examResultRepository;
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ExamResultDTO saveExamResult(ExamResultDTO examResultDTO) {
        // Check if result already exists
        if (examResultRepository.existsByExamExamIdAndStudentStudentID(
                examResultDTO.getExamId(), examResultDTO.getStudentId())) {
            throw new AuthException("Result already exists for this exam and student");
        }

        return createOrUpdateResult(examResultDTO, null);
    }

    @Override
    public ExamResultDTO updateExamResult(Long resultId, ExamResultDTO examResultDTO) {
        ExamResult existingResult = examResultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam result not found with ID: " + resultId));
        
        return createOrUpdateResult(examResultDTO, existingResult);
    }

    @Override
    public ExamResultDTO getExamResultById(Long resultId) {
        ExamResult result = examResultRepository.findById(resultId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam result not found with ID: " + resultId));
        return mapToDTO(result);
    }

    @Override
    public ExamResultDTO getExamResultByExamAndStudent(Long examId, Long studentId) {
        ExamResult result = examResultRepository.findByExamExamIdAndStudentStudentID(examId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Exam result not found for exam ID: " + examId + " and student ID: " + studentId));
        return mapToDTO(result);
    }

    @Override
    public List<ExamResultDTO> getResultsByExam(Long examId) {
        List<ExamResult> results = examResultRepository.findByExamExamIdOrderByMarksObtainedDesc(examId);
        return results.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExamResultDTO> getResultsByStudent(Long studentId) {
        List<ExamResult> results = examResultRepository.findByStudentStudentIDOrderByExamExamDateDesc(studentId);
        return results.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExamResultDTO> getResultsByStudentAndSubject(Long studentId, Long subjectId) {
        List<ExamResult> results = examResultRepository.findByStudentAndSubject(studentId, subjectId);
        return results.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExamResultDTO> getResultsByClassAndExam(Long classId, Long examId) {
        List<ExamResult> results = examResultRepository.findByClassAndExam(classId, examId);
        return results.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ExamResultDTO> getTopPerformersByExam(Long examId, Integer limit) {
        List<ExamResult> results = examResultRepository.findTopPerformersByExam(examId);
        return results.stream()
                .limit(limit != null ? limit : 10)
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ExamResultDTO> getFailedStudentsByExam(Long examId) {
        List<ExamResult> results = examResultRepository.findFailedStudentsByExam(examId);
        return results.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getExamResultStats(Long examId) {
        Map<String, Object> stats = new HashMap<>();
        List<Object[]> resultStats = examResultRepository.getResultStatsByExam(examId);
        
        for (Object[] stat : resultStats) {
            stats.put(stat[0].toString().toLowerCase() + "Count", stat[1]);
        }
        
        Double classAverage = examResultRepository.getClassAverageByExam(examId);
        stats.put("classAverage", classAverage);
        
        return stats;
    }

    @Override
    public Map<String, Object> getGradeDistribution(Long examId) {
        Map<String, Object> distribution = new HashMap<>();
        List<Object[]> gradeStats = examResultRepository.getGradeDistributionByExam(examId);
        
        for (Object[] stat : gradeStats) {
            distribution.put(stat[0].toString(), stat[1]);
        }
        
        return distribution;
    }

    @Override
    public Map<String, Object> getStudentOverallPerformance(Long studentId) {
        Map<String, Object> performance = new HashMap<>();
        List<Object[]> stats = examResultRepository.getStudentOverallPerformance(studentId);
        
        for (Object[] stat : stats) {
            performance.put(stat[0].toString().toLowerCase() + "Count", stat[1]);
        }
        
        return performance;
    }

    @Override
    public Double getClassAverageByExam(Long examId) {
        return examResultRepository.getClassAverageByExam(examId);
    }

    @Override
    public Double getStudentAverageBySubject(Long studentId, Long subjectId) {
        return examResultRepository.getStudentAverageBySubject(studentId, subjectId);
    }

    @Override
    public List<ExamResultDTO> bulkUploadResults(Long examId, List<ExamResultDTO> results) {
        // Verify exam exists
        if (!examRepository.existsById(examId)) {
            throw new ResourceNotFoundException("Exam not found with ID: " + examId);
        }

        List<ExamResult> savedResults = results.stream()
                .map(dto -> {
                    dto.setExamId(examId);
                    return createOrUpdateResult(dto, null);
                })
                .map(dto -> examResultRepository.findByExamExamIdAndStudentStudentID(dto.getExamId(), dto.getStudentId()).orElse(null))
                .collect(Collectors.toList());

        return savedResults.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteExamResult(Long resultId) {
        if (!examResultRepository.existsById(resultId)) {
            throw new ResourceNotFoundException("Exam result not found with ID: " + resultId);
        }
        examResultRepository.deleteById(resultId);
    }

    @Override
    public Map<String, Object> generateReportCard(Long studentId, Long classId) {
        Map<String, Object> reportCard = new HashMap<>();
        
        // Get student info
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));
        
        reportCard.put("studentName", student.getFirstName() + " " + student.getLastName());
        reportCard.put("studentRoll", student.getRoll());
        reportCard.put("className", student.getStudentClass().getClassName());
        
        // Get all results for the student
        List<ExamResultDTO> results = getResultsByStudent(studentId);
        reportCard.put("results", results);
        
        // Calculate overall performance
        Map<String, Object> overallPerformance = getStudentOverallPerformance(studentId);
        reportCard.put("overallPerformance", overallPerformance);
        
        return reportCard;
    }

    /**
     * Helper method to create or update exam result
     */
    private ExamResultDTO createOrUpdateResult(ExamResultDTO examResultDTO, ExamResult existingResult) {
        // Get exam and student entities
        Exam exam = examRepository.findById(examResultDTO.getExamId())
                .orElseThrow(() -> new ResourceNotFoundException("Exam not found with ID: " + examResultDTO.getExamId()));
        
        Student student = studentRepository.findById(examResultDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + examResultDTO.getStudentId()));

        // Get current user as evaluator
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User evaluator = userRepository.findByUsernameOrEmail(currentUsername, currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + currentUsername));

        ExamResult result = existingResult != null ? existingResult : new ExamResult();
        
        result.setExam(exam);
        result.setStudent(student);
        result.setMarksObtained(examResultDTO.getMarksObtained());
        result.setTotalMarks(examResultDTO.getTotalMarks());
        result.setRemarks(examResultDTO.getRemarks());
        result.setIsAbsent(examResultDTO.getIsAbsent() != null ? examResultDTO.getIsAbsent() : false);
        result.setEvaluatedBy(evaluator);
        result.setEvaluationDate(LocalDate.now());
        
        if (existingResult == null) {
            result.setCreatedAt(LocalDate.now());
        }
        result.setUpdatedAt(LocalDate.now());

        // Calculate grade and result status
        result.calculateResult();

        ExamResult savedResult = examResultRepository.save(result);
        return mapToDTO(savedResult);
    }

    /**
     * Helper method to map ExamResult entity to DTO
     */
    private ExamResultDTO mapToDTO(ExamResult result) {
        ExamResultDTO dto = modelMapper.map(result, ExamResultDTO.class);
        dto.setExamId(result.getExam().getExamId());
        dto.setExamName(result.getExam().getExamName());
        dto.setSubjectName(result.getExam().getSubject().getSubjectName());
        dto.setStudentId(result.getStudent().getStudentID());
        dto.setStudentName(result.getStudent().getFirstName() + " " + result.getStudent().getLastName());
        dto.setStudentRoll(String.valueOf(result.getStudent().getRoll()));
        dto.setEvaluatedBy(result.getEvaluatedBy().getId());
        dto.setEvaluatedByName(result.getEvaluatedBy().getName());
        
        // Calculate percentage
        if (result.getTotalMarks() != null && result.getTotalMarks() > 0) {
            dto.setPercentage((result.getMarksObtained() / result.getTotalMarks()) * 100);
        }
        
        return dto;
    }
}
