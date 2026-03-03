package hr.talent.acquisition.criteria.levels;

import hr.talent.acquisition.enums.DeveloperLevel;
import hr.talent.acquisition.criteria.ScreeningCriteria;

/**
 * Screening criteria for mid-level (2-5 years) developers.
 * Balanced focus on technical depth and emerging leadership.
 */
public class MidLevelCriteria extends ScreeningCriteria {

    public MidLevelCriteria() {
        super(DeveloperLevel.MID);
    }

    @Override
    protected void configureWeights() {
        // Mid level: Balanced across technical and platform skills
        categoryWeights.put("TECHNICAL_DEPTH", 0.30);
        categoryWeights.put("CLOUD_SAAS_PLATFORM", 0.25);
        categoryWeights.put("PRODUCT_SAAS_UNDERSTANDING", 0.10);
        categoryWeights.put("ENGINEERING_MATURITY", 0.25);
        categoryWeights.put("SIGNAL_STRENGTH", 0.10);
    }

    @Override
    public String getEvaluationPrompt() {
        return "MID-LEVEL SOFTWARE DEVELOPER SCREENING (2-5 Years Experience)\n" +
            "\n" +
            "EVALUATION FOCUS:\n" +
            "- Solid production experience with modern tech stacks\n" +
            "- Can own features end-to-end\n" +
            "- Growing expertise in specific domains\n" +
            "- Contributes to code reviews and documentation\n" +
            "- Starting to mentor juniors\n" +
            "\n" +
            "SCORING GUIDELINES:\n" +
            "\n" +
            "1. TECHNICAL_DEPTH (30%):\n" +
            "   - Proficient in backend (Spring Boot, Express, Django, etc.)\n" +
            "   - Frontend competency (React, Vue, Angular)\n" +
            "   - Strong SQL and data modeling skills\n" +
            "   - Understands API design best practices\n" +
            "   - Experience with microservices or modular monoliths\n" +
            "   Score 80+ if: Full-stack capability, owned significant features\n" +
            "   Score 60-79 if: Strong in one area, basic in others\n" +
            "   Score below 60 if: Still junior-level depth\n" +
            "\n" +
            "2. CLOUD_SAAS_PLATFORM (25%):\n" +
            "   - Hands-on with at least one cloud provider\n" +
            "   - Experience with CI/CD pipelines\n" +
            "   - Container experience (Docker, basic K8s)\n" +
            "   - Understands monitoring and logging\n" +
            "   - Has handled production deployments\n" +
            "   Score 80+ if: DevOps capable, IaC experience\n" +
            "   Score 60-79 if: Uses cloud but doesn't architect\n" +
            "   Score below 60 if: Limited cloud hands-on\n" +
            "\n" +
            "3. PRODUCT_SAAS_UNDERSTANDING (10%):\n" +
            "   - Understands feature flags, A/B testing concepts\n" +
            "   - Basic multi-tenancy awareness\n" +
            "   - Thinks about user experience impact\n" +
            "   Score 80+ if: Has worked on SaaS products\n" +
            "   Score 60-79 if: General product awareness\n" +
            "   Score below 60 if: Pure technical focus\n" +
            "\n" +
            "4. ENGINEERING_MATURITY (25%):\n" +
            "   - Strong testing practices (unit, integration)\n" +
            "   - Active in code reviews\n" +
            "   - Good documentation habits\n" +
            "   - Cross-team collaboration experience\n" +
            "   - Has mentored or onboarded others\n" +
            "   Score 80+ if: Role model engineering practices\n" +
            "   Score 60-79 if: Good individual practices\n" +
            "   Score below 60 if: Inconsistent quality\n" +
            "\n" +
            "5. SIGNAL_STRENGTH (10%):\n" +
            "   - Quality company experience\n" +
            "   - Impact metrics in resume\n" +
            "   - Consistent growth pattern\n" +
            "   - OSS contributions are bonus\n" +
            "   Score 80+ if: Clear achievements, recognized work\n" +
            "   Score 60-79 if: Solid background\n" +
            "   Score below 60 if: Vague accomplishments\n";
    }
}
