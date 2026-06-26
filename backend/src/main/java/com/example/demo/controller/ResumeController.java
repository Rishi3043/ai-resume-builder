package com.example.demo.controller;

import com.example.demo.entity.Resume;
import com.example.demo.service.AtsAnalyzerService;
import com.example.demo.dto.AtsAnalysisResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JobMatchService;
import com.example.demo.dto.JobMatchResponse;
import com.example.demo.service.PdfService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resumes")
@CrossOrigin(origins = "*")
public class ResumeController {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final AtsAnalyzerService atsAnalyzerService;
    private final JobMatchService jobMatchService;
    private final PdfService pdfService;

    public ResumeController(
            ResumeRepository resumeRepository,
            UserRepository userRepository,
            AtsAnalyzerService atsAnalyzerService,
            JobMatchService jobMatchService,
            PdfService pdfService) {

        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.atsAnalyzerService = atsAnalyzerService;
        this.jobMatchService = jobMatchService;
        this.pdfService = pdfService;
    }

    // ✅ CREATE RESUME WITH IMAGE (🔥 FINAL FIX)
    @PostMapping(value = "/user/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resume> createResume(
            @PathVariable Long userId,
            @RequestPart("resume") Resume resume,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        try {
            // 🔥 DEBUG
            System.out.println("📥 RECEIVED RESUME: " + resume.getFullName());
            System.out.println("📸 IMAGE PRESENT: " + (image != null));

            // ✅ attach image
            if (image != null && !image.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
                resume.setProfileImage(base64Image);
                System.out.println("✅ IMAGE SAVED: " + image.getSize());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        resume.setUser(user);

        if (resume.getTemplate() == null || resume.getTemplate().isEmpty()) {
            resume.setTemplate("modern");
        }

        Resume saved = resumeRepository.save(resume);

        System.out.println("💾 SAVED RESUME ID: " + saved.getId());

        return ResponseEntity.ok(saved);
    }

    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<Resume>> getAllResumes() {
        return ResponseEntity.ok(resumeRepository.findAll());
    }

    // ✅ GET BY USER
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Resume>> getResumesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(resumeRepository.findByUserId(userId));
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Resume> getResumeById(@PathVariable Long id) {
        return ResponseEntity.ok(
                resumeRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    // ✅ UPDATE RESUME
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resume> updateResume(
            @PathVariable Long id,
            @RequestPart("resume") Resume resume,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        Resume existingResume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        try {
            // Update fields
            existingResume.setFullName(resume.getFullName());
            existingResume.setTitle(resume.getTitle());
            existingResume.setEmail(resume.getEmail());
            existingResume.setPhone(resume.getPhone());
            existingResume.setLocation(resume.getLocation());
            existingResume.setLinkedin(resume.getLinkedin());
            existingResume.setGithub(resume.getGithub());
            existingResume.setSummary(resume.getSummary());
            existingResume.setSkills(resume.getSkills());
            existingResume.setExperience(resume.getExperience());
            existingResume.setTemplate(resume.getTemplate());

            // Update image only if new one is provided
            if (image != null && !image.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(image.getBytes());
                existingResume.setProfileImage(base64Image);
                System.out.println("✅ IMAGE UPDATED: " + image.getSize());
            }
            // If no new image, keep existing profile_image unchanged

        } catch (Exception e) {
            e.printStackTrace();
        }

        Resume saved = resumeRepository.save(existingResume);
        return ResponseEntity.ok(saved);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable Long id) {
        resumeRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }

    // ✅ PDF DOWNLOAD
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadResume(@PathVariable Long id) {

        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));

        byte[] pdf = pdfService.generatePdf(resume);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    // ✅ ATS ANALYZE
    @GetMapping("/{id}/analyze")
    public ResponseEntity<AtsAnalysisResponse> analyzeResume(@PathVariable Long id) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        AtsAnalysisResponse response = atsAnalyzerService.analyzeResume(resume);
        return ResponseEntity.ok(response);
    }

    // ✅ JOB MATCH
    @PostMapping("/{id}/match")
    public ResponseEntity<JobMatchResponse> matchJob(
            @PathVariable Long id,
            @RequestBody String jobDescription) {
        Resume resume = resumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        JobMatchResponse response = jobMatchService.analyzeJobMatch(resume, jobDescription);
        return ResponseEntity.ok(response);
    }
}