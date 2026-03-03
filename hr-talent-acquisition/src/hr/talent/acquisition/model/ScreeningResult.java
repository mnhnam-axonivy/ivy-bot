package hr.talent.acquisition.model;

import java.util.HashMap;
import java.util.Map;

import dev.langchain4j.model.output.structured.Description;
import hr.talent.acquisition.enums.DeveloperLevel;
import hr.talent.acquisition.enums.ScreeningRecommendation;

/**
 * Holds the evaluation outcome for a candidate.
 */
public class ScreeningResult {
    
    @Description("Name of the candidate being evaluated")
    private String candidateName;
    
    @Description("Developer level the candidate was evaluated for: ENTRY, MID, or SENIOR")
    private DeveloperLevel evaluatedLevel;
    
    @Description("Overall screening score from 0-100")
    private double overallScore;
    
    @Description("Individual scores for each evaluation category")
    private Map<String, Double> categoryScores;
    
    @Description("Detailed feedback for each evaluation category")
    private Map<String, String> categoryFeedback;
    
    @Description("Final recommendation: STRONG_YES, YES, MAYBE, NO, or STRONG_NO")
    private ScreeningRecommendation recommendation;
    
    @Description("Detailed analysis of the candidate's strengths and weaknesses")
    private String detailedAnalysis;

    public ScreeningResult() {
        this.categoryScores = new HashMap<>();
        this.categoryFeedback = new HashMap<>();
    }

    public ScreeningResult(String candidateName, DeveloperLevel evaluatedLevel) {
        this();
        this.candidateName = candidateName;
        this.evaluatedLevel = evaluatedLevel;
    }

    public void addCategoryScore(String category, double score, String feedback) {
        this.categoryScores.put(category, score);
        this.categoryFeedback.put(category, feedback);
    }

    public void computeOverallScore(Map<String, Double> weights) {
        double totalScore = 0.0;
        double totalWeight = 0.0;
        
        for (Map.Entry<String, Double> entry : weights.entrySet()) {
            String category = entry.getKey();
            Double weight = entry.getValue();
            Double score = categoryScores.getOrDefault(category, 0.0);
            totalScore += score * weight;
            totalWeight += weight;
        }
        
        this.overallScore = totalWeight > 0 ? totalScore / totalWeight : 0.0;
        this.recommendation = ScreeningRecommendation.fromScore(this.overallScore);
    }

    public String getCandidateName() { return candidateName; }
    public void setCandidateName(String candidateName) { this.candidateName = candidateName; }
    
    public DeveloperLevel getEvaluatedLevel() { return evaluatedLevel; }
    public void setEvaluatedLevel(DeveloperLevel evaluatedLevel) { this.evaluatedLevel = evaluatedLevel; }
    
    public double getOverallScore() { return overallScore; }
    public void setOverallScore(double overallScore) { this.overallScore = overallScore; }
    
    public Map<String, Double> getCategoryScores() { return categoryScores; }
    public void setCategoryScores(Map<String, Double> scores) { this.categoryScores = scores; }
    
    public Map<String, String> getCategoryFeedback() { return categoryFeedback; }
    public void setCategoryFeedback(Map<String, String> feedback) { this.categoryFeedback = feedback; }
    
    public ScreeningRecommendation getRecommendation() { return recommendation; }
    public void setRecommendation(ScreeningRecommendation recommendation) { this.recommendation = recommendation; }
    
    public String getDetailedAnalysis() { return detailedAnalysis; }
    public void setDetailedAnalysis(String analysis) { this.detailedAnalysis = analysis; }

    public String formatReport() {
        StringBuilder report = new StringBuilder();
        report.append("###############################################################\n");
        report.append("#                    SCREENING REPORT                         #\n");
        report.append("###############################################################\n\n");
        report.append("Candidate: ").append(candidateName).append("\n");
        report.append("Evaluated For: ").append(evaluatedLevel).append(" Level\n");
        report.append("Overall Score: ").append(String.format("%.1f", overallScore)).append("/100\n");
        report.append("Recommendation: ").append(recommendation).append("\n\n");
        
        report.append("CATEGORY BREAKDOWN\n");
        report.append("------------------\n");
        for (Map.Entry<String, Double> entry : categoryScores.entrySet()) {
            report.append(String.format("%-30s: %5.1f/100\n", entry.getKey(), entry.getValue()));
            String feedback = categoryFeedback.get(entry.getKey());
            if (feedback != null) {
                report.append("  -> ").append(feedback).append("\n");
            }
        }
        
        if (detailedAnalysis != null) {
            report.append("\nDETAILED ANALYSIS\n");
            report.append("-----------------\n");
            report.append(detailedAnalysis).append("\n");
        }
        
        return report.toString();
    }
}
