package com.smsytem.students.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smsytem.students.entity.ClassOrSection;


public interface ClassRepository extends JpaRepository<ClassOrSection, Long> {
    @Query("SELECT s.subjectID FROM ClassOrSection c JOIN c.subjects s WHERE c.classID = :classID")
    Set<Long> findSubjectIDsByClassID(@Param("classID") Long classID);
}
