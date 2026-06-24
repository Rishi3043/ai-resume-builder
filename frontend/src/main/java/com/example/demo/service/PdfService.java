package com.example.demo.service;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Resume;
import com.example.demo.entity.Experience;
import com.example.demo.entity.Education;
import com.example.demo.entity.Project;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

@Service
public class PdfService {

    public byte[] generatePdf(Resume resume) {

        System.out.println("🔥 IMAGE NULL? " + (resume.getProfileImage() == null));
        System.out.println("🔥 IMAGE SIZE: " +
                (resume.getProfileImage() != null ? resume.getProfileImage().length : "NULL"));

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document(PageSize.A4, 40, 40, 50, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            String template = "modern";

            if (resume.getTemplate() != null && !resume.getTemplate().trim().isEmpty()) {
                template = resume.getTemplate().toLowerCase().trim();
            }

            System.out.println("🔥 TEMPLATE: " + template);

            switch (template) {
                case "minimal":
                    generateMinimal(document, resume);
                    break;

                case "creative":
                    generateCreative(document, resume);
                    break;

                default:
                    generateModern(document, resume);
            }

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("PDF generation failed");
        }

        return out.toByteArray();
    }

    // =========================
    // 🔹 MODERN TEMPLATE
    // =========================
    private void generateModern(Document doc, Resume resume) throws Exception {

        Font nameFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, BaseColor.WHITE);
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 11);

        // =========================
        // MAIN TABLE (SIDEBAR + CONTENT)
        // =========================
        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new float[]{1, 2}); // sidebar smaller

        // =========================
        // 🔵 LEFT SIDEBAR
        // =========================
        PdfPCell leftCell = new PdfPCell();
        leftCell.setBackgroundColor(new BaseColor(41, 128, 185)); // blue
        leftCell.setPadding(10);
        leftCell.setBorder(Rectangle.NO_BORDER);

        // PROFILE IMAGE
        if (resume.getProfileImage() != null && resume.getProfileImage().length > 0) {
            try {
                Image img = Image.getInstance(resume.getProfileImage());
                img.scaleToFit(80, 80);
                img.setAlignment(Image.ALIGN_CENTER);
                leftCell.addElement(img);
                leftCell.addElement(new Paragraph(" "));
            } catch (Exception e) {
                System.out.println("Image error");
            }
        }

        // NAME
        Paragraph name = new Paragraph(safe(resume.getFullName()), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        leftCell.addElement(name);
        leftCell.addElement(new Paragraph(" "));

        // CONTACT
        Font whiteText = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.WHITE);

        leftCell.addElement(new Paragraph("EMAIL:", sectionFont));
        leftCell.addElement(new Paragraph(safe(resume.getEmail()), whiteText));

        leftCell.addElement(new Paragraph("PHONE:", sectionFont));
        leftCell.addElement(new Paragraph(safe(resume.getPhone()), whiteText));

        leftCell.addElement(new Paragraph("LOCATION:", sectionFont));
        leftCell.addElement(new Paragraph(safe(resume.getLocation()), whiteText));

        leftCell.addElement(new Paragraph(" "));

        // SKILLS
        leftCell.addElement(new Paragraph("SKILLS", sectionFont));

        if (resume.getSkills() != null) {
            for (String skill : resume.getSkills().split(",")) {
                leftCell.addElement(new Paragraph("• " + skill.trim(), whiteText));
            }
        }

        // =========================
        // ⚪ RIGHT CONTENT
        // =========================
        PdfPCell rightCell = new PdfPCell();
        rightCell.setPadding(15);
        rightCell.setBorder(Rectangle.NO_BORDER);

        // SUMMARY
        if (resume.getSummary() != null) {
            rightCell.addElement(new Paragraph("SUMMARY", sectionFont));
            rightCell.addElement(new Paragraph(resume.getSummary(), textFont));
            rightCell.addElement(new Paragraph(" "));
        }

        // EXPERIENCE
        if (resume.getExperiences() != null && !resume.getExperiences().isEmpty()) {
            rightCell.addElement(new Paragraph("EXPERIENCE", sectionFont));

            for (Experience exp : resume.getExperiences()) {

                rightCell.addElement(new Paragraph(
                        safe(exp.getJobTitle()) + " - " + safe(exp.getCompany()),
                        textFont));

                rightCell.addElement(new Paragraph(
                        safe(exp.getStartDate()) + " - " + safe(exp.getEndDate()),
                        textFont));

                rightCell.addElement(new Paragraph(safe(exp.getDescription()), textFont));
                rightCell.addElement(new Paragraph(" "));
            }
        }

        // EDUCATION
        if (resume.getEducations() != null && !resume.getEducations().isEmpty()) {

            rightCell.addElement(new Paragraph("EDUCATION", sectionFont));

            for (Education edu : resume.getEducations()) {

                rightCell.addElement(new Paragraph(
                        safe(edu.getDegree()) + " - " + safe(edu.getInstitution()),
                        textFont));

                rightCell.addElement(new Paragraph("Year: " + safe(edu.getYear()), textFont));
                rightCell.addElement(new Paragraph("CGPA: " + safe(edu.getCgpa()), textFont));

                rightCell.addElement(new Paragraph(" "));
            }
        }

        // PROJECTS
        if (resume.getProjects() != null && !resume.getProjects().isEmpty()) {

            rightCell.addElement(new Paragraph("PROJECTS", sectionFont));

            for (Project p : resume.getProjects()) {

                rightCell.addElement(new Paragraph(
                        safe(p.getTitle()) + " (" + safe(p.getTechnologies()) + ")",
                        textFont));

                rightCell.addElement(new Paragraph(safe(p.getDescription()), textFont));
                rightCell.addElement(new Paragraph(" "));
            }
        }

        // =========================
        // ADD TO TABLE
        // =========================
        mainTable.addCell(leftCell);
        mainTable.addCell(rightCell);

        doc.add(mainTable);
    }

    // =========================
    // 🔹 MINIMAL TEMPLATE
    // =========================
    private void generateMinimal(Document doc, Resume r) throws Exception {

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 11);

        addProfileImage(doc, r);

        doc.add(new Paragraph(safe(r.getFullName()), headerFont));
        doc.add(new Paragraph(safe(r.getEmail()) + " | " + safe(r.getPhone()), textFont));
        doc.add(new Paragraph(" "));

        addSection(doc, "SUMMARY", r.getSummary(), headerFont, textFont);
        addSkillsPlain(doc, r, headerFont, textFont);
        addExperience(doc, r, headerFont, textFont);
        addEducation(doc, r, headerFont, textFont);
    }

    // =========================
    // 🔹 CREATIVE TEMPLATE
    // =========================
    private void generateCreative(Document doc, Resume resume) throws Exception {

        addProfileImage(doc, resume);

        Font nameFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD, BaseColor.WHITE);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell(new Phrase(safe(resume.getFullName()), nameFont));
        cell.setBackgroundColor(new BaseColor(41, 128, 185));
        cell.setPadding(15);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(cell);
        doc.add(table);

        doc.add(new Paragraph(" "));

        Font headerFont = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD, BaseColor.BLUE);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12);

        addSection(doc, "SUMMARY", resume.getSummary(), headerFont, textFont);
        addSkills(doc, resume, headerFont, textFont);
        addExperience(doc, resume, headerFont, textFont);
        addProjects(doc, resume, headerFont, textFont);
    }

    // =========================
    // ✅ FINAL IMAGE METHOD (FIXED)
    // =========================
    private void addProfileImage(Document doc, Resume resume) {

        try {
            if (resume.getProfileImage() != null && resume.getProfileImage().length > 0) {

                System.out.println("📸 ADDING IMAGE TO PDF...");

                byte[] imageBytes = resume.getProfileImage();

                Image img = Image.getInstance(imageBytes);

                img.scaleToFit(100, 100);
                img.setAlignment(Image.ALIGN_RIGHT);

                doc.add(img);
                doc.add(new Paragraph(" "));

            } else {
                System.out.println("❌ IMAGE EMPTY OR NULL");
            }

        } catch (BadElementException e) {
            System.out.println("❌ BAD IMAGE FORMAT");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("❌ IMAGE ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =========================
    // COMMON METHODS
    // =========================

    private void addHeaderCenter(Document doc, Resume r, Font nameFont, Font textFont) throws Exception {

        Paragraph name = new Paragraph(safe(r.getFullName()), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        doc.add(name);

        Paragraph contact = new Paragraph(
                safe(r.getEmail()) + " | " +
                        safe(r.getPhone()) + " | " +
                        safe(r.getLocation()),
                textFont
        );
        contact.setAlignment(Element.ALIGN_CENTER);
        doc.add(contact);

        doc.add(new Paragraph(" "));
    }

    private void addSection(Document doc, String title, String content, Font headerFont, Font textFont) throws Exception {

        if (content != null && !content.trim().isEmpty()) {
            doc.add(new Paragraph(title, headerFont));
            doc.add(new Paragraph(content, textFont));
            doc.add(new Paragraph(" "));
        }
    }

    private void addSummary(Document doc, Resume r, Font headerFont, Font textFont) throws Exception {

        if (r.getSummary() != null && !r.getSummary().trim().isEmpty()) {

            doc.add(new Paragraph("SUMMARY", headerFont));
            doc.add(new Paragraph(r.getSummary(), textFont));
            doc.add(new Paragraph(" "));
        }
    }

    private void addSkills(Document doc, Resume r, Font headerFont, Font textFont) throws Exception {

        if (r.getSkills() != null && !r.getSkills().isEmpty()) {

            doc.add(new Paragraph("SKILLS", headerFont));

            for (String skill : r.getSkills().split(",")) {
                doc.add(new Paragraph("• " + skill.trim(), textFont));
            }

            doc.add(new Paragraph(" "));
        }
    }

    private void addSkillsPlain(Document doc, Resume r, Font headerFont, Font textFont) throws Exception {

        if (r.getSkills() != null && !r.getSkills().isEmpty()) {

            doc.add(new Paragraph("SKILLS", headerFont));
            doc.add(new Paragraph(r.getSkills(), textFont));
            doc.add(new Paragraph(" "));
        }
    }

    private void addExperience(Document doc, Resume r, Font headerFont, Font textFont) throws Exception {

        if (r.getExperiences() != null && !r.getExperiences().isEmpty()) {

            doc.add(new Paragraph("EXPERIENCE", headerFont));

            for (Experience exp : r.getExperiences()) {

                doc.add(new Paragraph(
                        safe(exp.getJobTitle()) + " - " + safe(exp.getCompany()),
                        textFont));

                doc.add(new Paragraph(
                        safe(exp.getStartDate()) + " - " + safe(exp.getEndDate()),
                        textFont));

                doc.add(new Paragraph(safe(exp.getDescription()), textFont));
                doc.add(new Paragraph(" "));
            }
        }
    }

    private void addEducation(Document doc, Resume r, Font headerFont, Font textFont) throws Exception {

        if (r.getEducations() != null && !r.getEducations().isEmpty()) {

            doc.add(new Paragraph("EDUCATION", headerFont));

            for (Education edu : r.getEducations()) {

                doc.add(new Paragraph(
                        safe(edu.getDegree()) + " - " + safe(edu.getInstitution()),
                        textFont));

                doc.add(new Paragraph("Year: " + safe(edu.getYear()), textFont));
                doc.add(new Paragraph("CGPA: " + safe(edu.getCgpa()), textFont));

                doc.add(new Paragraph(" "));
            }
        }
    }

    private void addProjects(Document doc, Resume r, Font headerFont, Font textFont) throws Exception {

        if (r.getProjects() != null && !r.getProjects().isEmpty()) {

            doc.add(new Paragraph("PROJECTS", headerFont));

            for (Project p : r.getProjects()) {

                doc.add(new Paragraph(
                        safe(p.getTitle()) + " (" + safe(p.getTechnologies()) + ")",
                        textFont));

                doc.add(new Paragraph(safe(p.getDescription()), textFont));
                doc.add(new Paragraph(" "));
            }
        }
    }

    private String safe(Object val) {
        return val == null ? "" : val.toString();
    }
}