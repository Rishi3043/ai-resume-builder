package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Resume;
import com.example.demo.entity.User;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.repository.UserRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private UserRepository userRepository;

    // Create Resume for a User
    public Resume createResume(Long userId, Resume resume) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            resume.setUser(user);
            return resumeRepository.save(resume);
        }

        return null;
    }

    // Get All Resumes
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    // Get Resume By Id
    public Resume getResumeById(Long id) {
        return resumeRepository.findById(id).orElse(null);
    }

    // Delete Resume
    public void deleteResume(Long id) {
        resumeRepository.deleteById(id);
    }
}
