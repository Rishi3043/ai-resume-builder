package com.example.demo.entity;

import jakarta.persistence.*;
import com.example.demo.entity.Resume;

@Entity
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String organization;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getOrganization() { return organization; }
    public void setOrganization(String organization) { this.organization = organization; }

    public Resume getResume() { return resume; }
    public void setResume(Resume resume) { this.resume = resume; }
}