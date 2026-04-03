package com.example.demo.entity;

import jakarta.persistence.*;
import com.example.demo.entity.Resume;

@Entity
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String institution;
    private String year;
    private String cgpa;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

    // Getters & Setters

    public Long getId() { 
        return id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public String getDegree() { 
        return degree; 
    }

    public void setDegree(String degree) { 
        this.degree = degree; 
    }

    public String getInstitution() { 
        return institution; 
    }

    public void setInstitution(String institution) { 
        this.institution = institution; 
    }

    public String getYear() { 
        return year; 
    }

    public void setYear(String year) { 
        this.year = year; 
    }

    public String getCgpa() { 
        return cgpa; 
    }

    public void setCgpa(String cgpa) { 
        this.cgpa = cgpa; 
    }

    public Resume getResume() { 
        return resume; 
    }

    public void setResume(Resume resume) { 
        this.resume = resume; 
    }
}