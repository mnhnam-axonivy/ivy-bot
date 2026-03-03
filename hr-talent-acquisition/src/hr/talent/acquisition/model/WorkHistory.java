package hr.talent.acquisition.model;

import java.util.ArrayList;
import java.util.List;

import dev.langchain4j.model.output.structured.Description;
import hr.talent.acquisition.enums.EmployerCategory;

/**
 * Represents a position held at a company.
 */
public class WorkHistory {
    
    @Description("Company or organization name")
    private String employer;
    
    @Description("Type of employer: FAANG, STARTUP, MIDSIZE_SAAS, ENTERPRISE, or CONSULTANCY")
    private EmployerCategory employerCategory;
    
    @Description("Job title or position held")
    private String jobTitle;
    
    @Description("Start date of employment (e.g., '2020-01' or 'January 2020')")
    private String periodStart;
    
    @Description("End date of employment or null/empty if currently employed")
    private String periodEnd;
    
    @Description("Main responsibilities and duties in this role")
    private List<String> keyDuties;
    
    @Description("Notable achievements, results, or impact delivered")
    private List<String> notableResults;
    
    @Description("Technologies and tools used in this position")
    private List<String> techStack;

    public WorkHistory() {
        this.keyDuties = new ArrayList<>();
        this.notableResults = new ArrayList<>();
        this.techStack = new ArrayList<>();
    }

    public WorkHistory(String employer, EmployerCategory employerCategory, String jobTitle, 
                       String periodStart, String periodEnd) {
        this();
        this.employer = employer;
        this.employerCategory = employerCategory;
        this.jobTitle = jobTitle;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }

    public WorkHistory withDuty(String duty) {
        this.keyDuties.add(duty);
        return this;
    }

    public WorkHistory withResult(String result) {
        this.notableResults.add(result);
        return this;
    }

    public WorkHistory withTech(String tech) {
        this.techStack.add(tech);
        return this;
    }

    public String getEmployer() { return employer; }
    public void setEmployer(String employer) { this.employer = employer; }
    
    public EmployerCategory getEmployerCategory() { return employerCategory; }
    public void setEmployerCategory(EmployerCategory employerCategory) { this.employerCategory = employerCategory; }
    
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }
    
    public String getPeriodStart() { return periodStart; }
    public void setPeriodStart(String periodStart) { this.periodStart = periodStart; }
    
    public String getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(String periodEnd) { this.periodEnd = periodEnd; }
    
    public List<String> getKeyDuties() { return keyDuties; }
    public void setKeyDuties(List<String> keyDuties) { this.keyDuties = keyDuties; }
    
    public List<String> getNotableResults() { return notableResults; }
    public void setNotableResults(List<String> notableResults) { this.notableResults = notableResults; }
    
    public List<String> getTechStack() { return techStack; }
    public void setTechStack(List<String> techStack) { this.techStack = techStack; }

    public String format() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("### %s @ %s (%s)\n", jobTitle, employer, employerCategory));
        output.append(String.format("Duration: %s to %s\n", periodStart, 
            periodEnd != null ? periodEnd : "Current"));
        
        if (!keyDuties.isEmpty()) {
            output.append("Key Duties:\n");
            keyDuties.forEach(d -> output.append("  - ").append(d).append("\n"));
        }
        
        if (!notableResults.isEmpty()) {
            output.append("Notable Results:\n");
            notableResults.forEach(r -> output.append("  * ").append(r).append("\n"));
        }
        
        if (!techStack.isEmpty()) {
            output.append("Tech Stack: ").append(String.join(" | ", techStack)).append("\n");
        }
        
        return output.toString();
    }
}
