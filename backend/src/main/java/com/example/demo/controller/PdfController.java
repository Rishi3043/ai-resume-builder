package com.example.demo.controller;

import com.example.demo.entity.Resume;
import com.example.demo.repository.ResumeRepository;
import com.example.demo.service.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")   // ✅ ADD THIS LINE
@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadResume(@PathVariable Long id) {

        Resume resume = resumeRepository.findById(id).orElseThrow();

        byte[] pdf = pdfService.generatePdf(resume);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resume.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}