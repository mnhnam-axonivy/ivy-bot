package hr.talent.acquisition;

import hr.talent.acquisition.criteria.ScreeningCriteria;
import hr.talent.acquisition.criteria.levels.EntryLevelCriteria;
import hr.talent.acquisition.criteria.levels.MidLevelCriteria;
import hr.talent.acquisition.criteria.levels.SeniorLevelCriteria;
import hr.talent.acquisition.enums.DeveloperLevel;
import hr.talent.acquisition.model.CandidateProfile;
import hr.talent.acquisition.model.ScreeningResult;

/**
 * Engine for screening candidates against level-specific criteria.
 */
public class ScreeningEngine {

    public static ScreeningCriteria getCriteriaForLevel(DeveloperLevel level) {
        switch (level) {
            case ENTRY:
                return new EntryLevelCriteria();
            case MID:
                return new MidLevelCriteria();
            case SENIOR:
                return new SeniorLevelCriteria();
            default:
                return new MidLevelCriteria();
        }
    }

    public static ScreeningCriteria getCriteriaForLevel(String levelName) {
        DeveloperLevel level = DeveloperLevel.valueOf(levelName.toUpperCase());
        return getCriteriaForLevel(level);
    }

    public static String buildScreeningPrompt(CandidateProfile candidate, DeveloperLevel level) {
        ScreeningCriteria criteria = getCriteriaForLevel(level);
        
        StringBuilder prompt = new StringBuilder();
        prompt.append("=== CV SCREENING REQUEST ===\n\n");
        prompt.append(criteria.getEvaluationPrompt());
        prompt.append("\n\n");
        prompt.append("=== CANDIDATE PROFILE TO EVALUATE ===\n\n");
        prompt.append(candidate.formatForScreening());
        prompt.append("\n\n");
        prompt.append("=== REQUIRED OUTPUT FORMAT ===\n");
        prompt.append("Provide scores (0-100) and brief justification for each category:\n");
        prompt.append("1. TECHNICAL_DEPTH: [score] - [justification]\n");
        prompt.append("2. CLOUD_SAAS_PLATFORM: [score] - [justification]\n");
        prompt.append("3. PRODUCT_SAAS_UNDERSTANDING: [score] - [justification]\n");
        prompt.append("4. ENGINEERING_MATURITY: [score] - [justification]\n");
        prompt.append("5. SIGNAL_STRENGTH: [score] - [justification]\n\n");
        prompt.append("Then provide:\n");
        prompt.append("- OVERALL_RECOMMENDATION: [STRONG_YES/YES/MAYBE/NO/STRONG_NO]\n");
        prompt.append("- KEY_STRENGTHS: [list top 3 strengths]\n");
        prompt.append("- AREAS_OF_CONCERN: [list any concerns]\n");
        prompt.append("- INTERVIEW_FOCUS: [suggested interview topics]\n");
        
        return prompt.toString();
    }

    public static ScreeningResult createEmptyResult(String candidateName, DeveloperLevel level) {
        return new ScreeningResult(candidateName, level);
    }
}
