package hr.talent.acquisition.model;

import dev.langchain4j.model.output.structured.Description;
import hr.talent.acquisition.enums.SkillDomain;

/**
 * Represents a technical competency from a candidate's profile.
 */
public class TechnicalSkill {
    
    @Description("Name of the technical skill or technology (e.g., Java, React, PostgreSQL)")
    private String skillName;
    
    @Description("Domain category: BACKEND, FRONTEND, DATABASE, CLOUD, DEVOPS, or ARCHITECTURE")
    private SkillDomain skillDomain;
    
    @Description("Total months of experience using this skill")
    private int monthsUsed;
    
    @Description("Proficiency score from 1 (beginner) to 10 (expert)")
    private int score;

    public TechnicalSkill() {}

    public TechnicalSkill(String skillName, SkillDomain skillDomain, int monthsUsed, int score) {
        this.skillName = skillName;
        this.skillDomain = skillDomain;
        this.monthsUsed = monthsUsed;
        this.score = score;
    }

    public static TechnicalSkill backend(String name, int months, int score) {
        return new TechnicalSkill(name, SkillDomain.BACKEND, months, score);
    }

    public static TechnicalSkill frontend(String name, int months, int score) {
        return new TechnicalSkill(name, SkillDomain.FRONTEND, months, score);
    }

    public static TechnicalSkill database(String name, int months, int score) {
        return new TechnicalSkill(name, SkillDomain.DATABASE, months, score);
    }

    public static TechnicalSkill cloud(String name, int months, int score) {
        return new TechnicalSkill(name, SkillDomain.CLOUD, months, score);
    }

    public static TechnicalSkill devops(String name, int months, int score) {
        return new TechnicalSkill(name, SkillDomain.DEVOPS, months, score);
    }

    public String getSkillName() { return skillName; }
    public void setSkillName(String skillName) { this.skillName = skillName; }
    
    public SkillDomain getSkillDomain() { return skillDomain; }
    public void setSkillDomain(SkillDomain skillDomain) { this.skillDomain = skillDomain; }
    
    public int getMonthsUsed() { return monthsUsed; }
    public void setMonthsUsed(int monthsUsed) { this.monthsUsed = monthsUsed; }
    
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public String format() {
        int years = monthsUsed / 12;
        int remainingMonths = monthsUsed % 12;
        String duration = years > 0 
            ? String.format("%dy %dm", years, remainingMonths)
            : String.format("%dm", remainingMonths);
        return String.format("%s [%s] - %s (score: %d/10)", skillName, skillDomain, duration, score);
    }
}
