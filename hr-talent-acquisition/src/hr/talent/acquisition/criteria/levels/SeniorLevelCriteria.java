package hr.talent.acquisition.criteria.levels;

import hr.talent.acquisition.enums.DeveloperLevel;
import hr.talent.acquisition.criteria.ScreeningCriteria;

/**
 * Screening criteria for senior (5+ years) developers.
 * Emphasis on architecture, leadership, and business impact.
 */
public class SeniorLevelCriteria extends ScreeningCriteria {

    public SeniorLevelCriteria() {
        super(DeveloperLevel.SENIOR);
    }

    @Override
    protected void configureWeights() {
        // Senior level: Architecture, platform expertise, and leadership
        categoryWeights.put("TECHNICAL_DEPTH", 0.30);
        categoryWeights.put("CLOUD_SAAS_PLATFORM", 0.25);
        categoryWeights.put("PRODUCT_SAAS_UNDERSTANDING", 0.15);
        categoryWeights.put("ENGINEERING_MATURITY", 0.20);
        categoryWeights.put("SIGNAL_STRENGTH", 0.10);
    }

    @Override
    public String getEvaluationPrompt() {
        return "SENIOR SOFTWARE DEVELOPER SCREENING (5+ Years Experience)\n" +
            "\n" +
            "EVALUATION FOCUS:\n" +
            "- System design and architecture ownership\n" +
            "- Technical leadership and decision making\n" +
            "- Cross-functional collaboration at strategic level\n" +
            "- Mentorship and team development\n" +
            "- Business impact and strategic thinking\n" +
            "\n" +
            "SCORING GUIDELINES:\n" +
            "\n" +
            "1. TECHNICAL_DEPTH (30%):\n" +
            "   - Expert-level in primary stack\n" +
            "   - Architected systems handling significant scale\n" +
            "   - Deep understanding of performance optimization\n" +
            "   - Experience with distributed systems patterns\n" +
            "   - API design leadership (REST, GraphQL, gRPC)\n" +
            "   - Has made significant technical decisions\n" +
            "   Score 80+ if: Architected production systems at scale\n" +
            "   Score 60-79 if: Strong technical but limited architecture ownership\n" +
            "   Score below 60 if: Mid-level depth despite years\n" +
            "\n" +
            "2. CLOUD_SAAS_PLATFORM (25%):\n" +
            "   - Multi-cloud or deep single-cloud expertise\n" +
            "   - Infrastructure-as-Code proficiency (Terraform, CDK)\n" +
            "   - Kubernetes orchestration experience\n" +
            "   - Security and compliance implementation\n" +
            "   - Cost optimization track record\n" +
            "   - Designed for high availability and disaster recovery\n" +
            "   Score 80+ if: Platform architect level\n" +
            "   Score 60-79 if: Strong practitioner\n" +
            "   Score below 60 if: Limited platform ownership\n" +
            "\n" +
            "3. PRODUCT_SAAS_UNDERSTANDING (15%):\n" +
            "   - Multi-tenant architecture design experience\n" +
            "   - Billing/subscription system knowledge\n" +
            "   - Feature flagging and experimentation at scale\n" +
            "   - Understanding of CAC, LTV, churn metrics\n" +
            "   - Has balanced technical debt vs feature velocity\n" +
            "   Score 80+ if: Has built/scaled SaaS products\n" +
            "   Score 60-79 if: Good product engineering sense\n" +
            "   Score below 60 if: Pure tech focus, no product thinking\n" +
            "\n" +
            "4. ENGINEERING_MATURITY (20%):\n" +
            "   - Drives engineering culture improvements\n" +
            "   - Establishes best practices and standards\n" +
            "   - Effective tech lead or architect experience\n" +
            "   - Strong cross-functional leadership\n" +
            "   - Incident commander experience\n" +
            "   - ADR and technical documentation ownership\n" +
            "   Score 80+ if: Organizational impact\n" +
            "   Score 60-79 if: Team-level leadership\n" +
            "   Score below 60 if: Still primarily IC\n" +
            "\n" +
            "5. SIGNAL_STRENGTH (10%):\n" +
            "   - Recognized expertise (talks, blogs, patents)\n" +
            "   - Quality company trajectory\n" +
            "   - Measurable business impact\n" +
            "   - Open source maintainer or major contributor\n" +
            "   - Industry recognition\n" +
            "   Score 80+ if: Industry recognized\n" +
            "   Score 60-79 if: Strong internal track record\n" +
            "   Score below 60 if: Unclear impact/trajectory\n";
    }
}
