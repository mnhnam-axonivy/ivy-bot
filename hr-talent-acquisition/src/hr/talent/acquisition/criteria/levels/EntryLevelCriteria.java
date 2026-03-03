package hr.talent.acquisition.criteria.levels;

import hr.talent.acquisition.enums.DeveloperLevel;
import hr.talent.acquisition.criteria.ScreeningCriteria;

/**
 * Screening criteria optimized for entry-level (0-2 years) developers.
 * Focus on potential, learning ability, and foundational skills.
 */
public class EntryLevelCriteria extends ScreeningCriteria {

    public EntryLevelCriteria() {
        super(DeveloperLevel.ENTRY);
    }

    @Override
    protected void configureWeights() {
        // Entry level: Focus more on signal strength (potential) and technical foundations
        categoryWeights.put("TECHNICAL_DEPTH", 0.35);          // Strong fundamentals required
        categoryWeights.put("CLOUD_SAAS_PLATFORM", 0.15);      // Basic awareness sufficient
        categoryWeights.put("PRODUCT_SAAS_UNDERSTANDING", 0.05); // Not expected at this level
        categoryWeights.put("ENGINEERING_MATURITY", 0.20);     // Code quality basics
        categoryWeights.put("SIGNAL_STRENGTH", 0.25);          // High - projects, learning trajectory
    }

    @Override
    public String getEvaluationPrompt() {
        return "ENTRY LEVEL SOFTWARE DEVELOPER SCREENING (0-2 Years Experience)\n" +
            "\n" +
            "EVALUATION FOCUS:\n" +
            "- Strong CS fundamentals and programming basics\n" +
            "- Eagerness to learn and growth mindset\n" +
            "- Personal projects, bootcamp work, or internship quality\n" +
            "- Basic understanding of version control and collaboration\n" +
            "- Problem-solving approach over deep expertise\n" +
            "\n" +
            "SCORING GUIDELINES:\n" +
            "\n" +
            "1. TECHNICAL_DEPTH (35%):\n" +
            "   - Knows at least one backend language well (Java, Python, Node.js)\n" +
            "   - Basic understanding of REST APIs\n" +
            "   - Can write clean, readable code\n" +
            "   - Familiar with basic data structures and algorithms\n" +
            "   Score 80+ if: Solid foundations, some framework experience\n" +
            "   Score 60-79 if: Good basics, limited practical application\n" +
            "   Score below 60 if: Gaps in fundamental knowledge\n" +
            "\n" +
            "2. CLOUD_SAAS_PLATFORM (15%):\n" +
            "   - Any cloud exposure (even free tier projects) is a plus\n" +
            "   - Understands basic deployment concepts\n" +
            "   - Familiar with containers conceptually\n" +
            "   Score 80+ if: Has deployed personal projects\n" +
            "   Score 60-79 if: Theoretical knowledge only\n" +
            "   Score below 60 if: No exposure\n" +
            "\n" +
            "3. PRODUCT_SAAS_UNDERSTANDING (5%):\n" +
            "   - Not expected - any awareness is bonus\n" +
            "   Score based on any demonstrated product thinking\n" +
            "\n" +
            "4. ENGINEERING_MATURITY (20%):\n" +
            "   - Uses version control properly\n" +
            "   - Writes tests (even basic ones)\n" +
            "   - Can work in a team (group projects, internships)\n" +
            "   - Good communication skills\n" +
            "   Score 80+ if: Good habits, collaborative\n" +
            "   Score 60-79 if: Individual contributor mindset\n" +
            "   Score below 60 if: Poor practices\n" +
            "\n" +
            "5. SIGNAL_STRENGTH (25%):\n" +
            "   - Quality of personal/university projects\n" +
            "   - GitHub activity and code quality\n" +
            "   - Learning trajectory (online courses, certifications)\n" +
            "   - Internship quality and feedback\n" +
            "   Score 80+ if: Active GitHub, quality projects, fast learner\n" +
            "   Score 60-79 if: Some activity, standard projects\n" +
            "   Score below 60 if: No public work, unclear trajectory\n";
    }
}
