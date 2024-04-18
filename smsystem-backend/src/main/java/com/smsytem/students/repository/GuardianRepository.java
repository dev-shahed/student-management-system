package com.smsytem.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsytem.students.entity.Guardian;

public interface GuardianRepository extends JpaRepository<Guardian, Long> {

}
