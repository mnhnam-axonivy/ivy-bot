package hr.talent.acquisition.model;

import dev.langchain4j.model.output.structured.Description;

/**
 * Represents academic credentials.
 */
public class EducationRecord {
    
    @Description("Name of the educational institution or university")
    private String school;
    
    @Description("Type of degree or qualification (e.g., Bachelor's, Master's, PhD, Bootcamp)")
    private String qualificationType;
    
    @Description("Field of study or major (e.g., Computer Science, Software Engineering)")
    private String studyArea;
    
    @Description("Year of graduation or completion")
    private String completionYear;
    
    @Description("Academic performance score (e.g., GPA, percentage, honors)")
    private String academicScore;

    public EducationRecord() {}

    public EducationRecord(String school, String qualificationType, 
                           String studyArea, String completionYear) {
        this.school = school;
        this.qualificationType = qualificationType;
        this.studyArea = studyArea;
        this.completionYear = completionYear;
    }

    public EducationRecord withScore(String score) {
        this.academicScore = score;
        return this;
    }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    
    public String getQualificationType() { return qualificationType; }
    public void setQualificationType(String qualificationType) { this.qualificationType = qualificationType; }
    
    public String getStudyArea() { return studyArea; }
    public void setStudyArea(String studyArea) { this.studyArea = studyArea; }
    
    public String getCompletionYear() { return completionYear; }
    public void setCompletionYear(String completionYear) { this.completionYear = completionYear; }
    
    public String getAcademicScore() { return academicScore; }
    public void setAcademicScore(String academicScore) { this.academicScore = academicScore; }

    public String format() {
        String scoreText = academicScore != null ? " | Score: " + academicScore : "";
        return String.format("%s, %s - %s (%s)%s", 
            qualificationType, studyArea, school, completionYear, scoreText);
    }
}
