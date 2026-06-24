package com.example.demo.controller;

import com.example.demo.entity.Template;
import com.example.demo.repository.TemplateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@CrossOrigin(origins = "*")
public class TemplateController {

    private final TemplateRepository templateRepository;

    public TemplateController(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @GetMapping
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    @PostMapping
    public Template createTemplate(@RequestBody Template template) {
        return templateRepository.save(template);
    }
}