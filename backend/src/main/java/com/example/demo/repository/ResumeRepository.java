package com.example.demo.repository;

import com.example.demo.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    // Get all resumes by user id
    List<Resume> findByUserId(Long userId);
}