package hr.talent.acquisition.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.langchain4j.model.output.structured.Description;
import hr.talent.acquisition.enums.DeveloperLevel;

/**
 * Complete candidate profile for screening.
 */
public class CandidateProfile {
    
    @Description("Unique identifier for the candidate")
    private String id;
    
    @Description("Full name of the candidate")
    private String fullName;
    
    @Description("Primary contact email address")
    private String contactEmail;
    
    @Description("Contact phone number")
    private String contactPhone;
    
    @Description("Current city or location of the candidate")
    private String currentLocation;
    
    @Description("LinkedIn profile URL")
    private String linkedInUrl;
    
    @Description("GitHub profile URL")
    private String githubUrl;
    
    @Description("Professional summary or objective statement from the candidate")
    private String professionalSummary;
    
    @Description("Developer seniority level: ENTRY (0-2 years), MID (2-5 years), or SENIOR (5+ years)")
    private DeveloperLevel level;
    
    @Description("AI-generated brief summary of the candidate's profile")
    private String aiGeneratedSummary;
    
    @Description("Original CV content in markdown or text format")
    private String rawCvContent;
    
    @Description("List of technical skills with proficiency levels")
    private List<TechnicalSkill> technicalSkills;
    
    @Description("Employment history with job titles, companies, and responsibilities")
    private List<WorkHistory> workHistories;
    
    @Description("Academic qualifications and degrees")
    private List<EducationRecord> educationRecords;
    
    @Description("Professional certifications (e.g., AWS Certified, Oracle Certified Java Programmer)")
    private List<String> professionalCertifications;
    
    @Description("Personal or open-source projects")
    private List<String> personalProjects;
    
    @Description("Technical blog posts, articles, or publications")
    private List<String> technicalWritings;

    @Description("Candidate's profile photo extracted from the CV, encoded as a base64 JPEG/PNG string (no data URI prefix)")
    private String face;

    public CandidateProfile() {
        this.technicalSkills = new ArrayList<>();
        this.workHistories = new ArrayList<>();
        this.educationRecords = new ArrayList<>();
        this.professionalCertifications = new ArrayList<>();
        this.personalProjects = new ArrayList<>();
        this.technicalWritings = new ArrayList<>();
    }

    public CandidateProfile(String fullName, String contactEmail) {
        this();
        this.fullName = fullName;
        this.contactEmail = contactEmail;
    }

    public CandidateProfile withSkill(TechnicalSkill skill) {
        this.technicalSkills.add(skill);
        return this;
    }

    public CandidateProfile withWorkHistory(WorkHistory history) {
        this.workHistories.add(history);
        return this;
    }

    public CandidateProfile withEducation(EducationRecord education) {
        this.educationRecords.add(education);
        return this;
    }

    public CandidateProfile withCertification(String cert) {
        this.professionalCertifications.add(cert);
        return this;
    }

    public CandidateProfile withProject(String project) {
        this.personalProjects.add(project);
        return this;
    }

    public CandidateProfile withWriting(String writing) {
        this.technicalWritings.add(writing);
        return this;
    }

    public CandidateProfile withSummary(String summary) {
        this.professionalSummary = summary;
        return this;
    }

    public CandidateProfile withLinkedIn(String url) {
        this.linkedInUrl = url;
        return this;
    }

    public CandidateProfile withGithub(String url) {
        this.githubUrl = url;
        return this;
    }

    public CandidateProfile withLocation(String location) {
        this.currentLocation = location;
        return this;
    }

    public CandidateProfile withPhone(String phone) {
        this.contactPhone = phone;
        return this;
    }

    public int calculateTotalExperienceMonths() {
        return workHistories.stream()
            .mapToInt(this::calculatePositionMonths)
            .sum();
    }

    private int calculatePositionMonths(WorkHistory wh) {
        // Simplified calculation - in production, parse actual dates
        return 24; // Default 2 years per position
    }

    public List<TechnicalSkill> getSkillsByDomain(String domain) {
        return technicalSkills.stream()
            .filter(s -> s.getSkillDomain() != null && s.getSkillDomain().name().equalsIgnoreCase(domain))
            .collect(Collectors.toList());
    }

    // All getters and setters
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    
    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }
    
    public String getLinkedInUrl() { return linkedInUrl; }
    public void setLinkedInUrl(String linkedInUrl) { this.linkedInUrl = linkedInUrl; }
    
    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    
    public String getProfessionalSummary() { return professionalSummary; }
    public void setProfessionalSummary(String professionalSummary) { this.professionalSummary = professionalSummary; }
    
    public List<TechnicalSkill> getTechnicalSkills() { return technicalSkills; }
    public void setTechnicalSkills(List<TechnicalSkill> technicalSkills) { this.technicalSkills = technicalSkills; }
    
    public List<WorkHistory> getWorkHistories() { return workHistories; }
    public void setWorkHistories(List<WorkHistory> workHistories) { this.workHistories = workHistories; }
    
    public List<EducationRecord> getEducationRecords() { return educationRecords; }
    public void setEducationRecords(List<EducationRecord> educationRecords) { this.educationRecords = educationRecords; }
    
    public List<String> getProfessionalCertifications() { return professionalCertifications; }
    public void setProfessionalCertifications(List<String> certs) { this.professionalCertifications = certs; }
    
    public List<String> getPersonalProjects() { return personalProjects; }
    public void setPersonalProjects(List<String> projects) { this.personalProjects = projects; }
    
    public List<String> getTechnicalWritings() { return technicalWritings; }
    public void setTechnicalWritings(List<String> writings) { this.technicalWritings = writings; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public DeveloperLevel getLevel() { return level; }
    public void setLevel(DeveloperLevel level) { this.level = level; }
    
    public String getAiGeneratedSummary() { return aiGeneratedSummary; }
    public void setAiGeneratedSummary(String summary) { this.aiGeneratedSummary = summary; }
    
    public String getRawCvContent() { return rawCvContent; }
    public void setRawCvContent(String content) { this.rawCvContent = content; }

    public String getFace() { return face; }
    public void setFace(String face) { this.face = face; }

    public String formatForScreening() {
        StringBuilder doc = new StringBuilder();
        doc.append("===============================================================\n");
        doc.append("CANDIDATE PROFILE: ").append(fullName).append("\n");
        doc.append("===============================================================\n\n");
        
        doc.append("CONTACT INFORMATION\n");
        doc.append("-------------------\n");
        doc.append("Email: ").append(contactEmail).append("\n");
        if (contactPhone != null) doc.append("Phone: ").append(contactPhone).append("\n");
        if (currentLocation != null) doc.append("Location: ").append(currentLocation).append("\n");
        if (linkedInUrl != null) doc.append("LinkedIn: ").append(linkedInUrl).append("\n");
        if (githubUrl != null) doc.append("GitHub: ").append(githubUrl).append("\n");
        doc.append("\n");
        
        if (professionalSummary != null) {
            doc.append("PROFESSIONAL SUMMARY\n");
            doc.append("--------------------\n");
            doc.append(professionalSummary).append("\n\n");
        }
        
        doc.append("TECHNICAL COMPETENCIES\n");
        doc.append("----------------------\n");
        technicalSkills.forEach(s -> doc.append("  - ").append(s.format()).append("\n"));
        doc.append("\n");
        
        doc.append("WORK HISTORY\n");
        doc.append("------------\n");
        workHistories.forEach(w -> doc.append(w.format()).append("\n"));
        
        doc.append("EDUCATION\n");
        doc.append("---------\n");
        educationRecords.forEach(e -> doc.append("  - ").append(e.format()).append("\n"));
        doc.append("\n");
        
        if (!professionalCertifications.isEmpty()) {
            doc.append("CERTIFICATIONS\n");
            doc.append("--------------\n");
            professionalCertifications.forEach(c -> doc.append("  - ").append(c).append("\n"));
            doc.append("\n");
        }
        
        if (!personalProjects.isEmpty()) {
            doc.append("PERSONAL PROJECTS\n");
            doc.append("-----------------\n");
            personalProjects.forEach(p -> doc.append("  - ").append(p).append("\n"));
            doc.append("\n");
        }
        
        if (!technicalWritings.isEmpty()) {
            doc.append("TECHNICAL WRITINGS\n");
            doc.append("------------------\n");
            technicalWritings.forEach(w -> doc.append("  - ").append(w).append("\n"));
            doc.append("\n");
        }
        
        return doc.toString();
    }
}
