package hr.talent.acquisition.criteria;

import java.util.HashMap;
import java.util.Map;

import hr.talent.acquisition.enums.DeveloperLevel;

/**
 * Base class for level-specific screening criteria.
 * Contains the 5 main evaluation dimensions with configurable weights.
 */
public abstract class ScreeningCriteria {
    
    protected DeveloperLevel targetLevel;
    protected Map<String, Double> categoryWeights;
    protected Map<String, String> categoryDescriptions;

    protected ScreeningCriteria(DeveloperLevel level) {
        this.targetLevel = level;
        this.categoryWeights = new HashMap<>();
        this.categoryDescriptions = new HashMap<>();
        initializeCategories();
        configureWeights();
    }

    private void initializeCategories() {
        categoryDescriptions.put("TECHNICAL_DEPTH", 
            "Backend/Frontend/Database engineering capabilities, API design, architecture patterns");
        categoryDescriptions.put("CLOUD_SAAS_PLATFORM", 
            "Cloud infrastructure (AWS/GCP/Azure), DevOps, CI/CD, scalability, security");
        categoryDescriptions.put("PRODUCT_SAAS_UNDERSTANDING", 
            "Multi-tenancy design, billing systems, user lifecycle, system economics");
        categoryDescriptions.put("ENGINEERING_MATURITY", 
            "Code quality, collaboration, leadership, project delivery");
        categoryDescriptions.put("SIGNAL_STRENGTH", 
            "GitHub activity, open-source, career trajectory, resume authenticity");
    }

    /**
     * Subclasses implement this to set level-specific weights.
     */
    protected abstract void configureWeights();

    /**
     * Get the evaluation prompt for AI screening.
     */
    public abstract String getEvaluationPrompt();

    public DeveloperLevel getTargetLevel() { return targetLevel; }
    public Map<String, Double> getCategoryWeights() { return categoryWeights; }
    public Map<String, String> getCategoryDescriptions() { return categoryDescriptions; }

    public String formatCriteriaDescription() {
        StringBuilder desc = new StringBuilder();
        desc.append("SCREENING CRITERIA FOR: ").append(targetLevel.getDisplayName()).append("\n");
        desc.append("===============================================================\n\n");
        
        for (Map.Entry<String, Double> entry : categoryWeights.entrySet()) {
            String category = entry.getKey();
            Double weight = entry.getValue();
            String description = categoryDescriptions.get(category);
            
            desc.append(String.format("%-30s Weight: %.0f%%\n", category, weight * 100));
            desc.append("  ").append(description).append("\n\n");
        }
        
        return desc.toString();
    }
}
