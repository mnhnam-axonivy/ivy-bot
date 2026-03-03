"""Generate 3 sample CV PDFs for hr-talent-acquisition test data."""

from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm
from reportlab.lib import colors
from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, HRFlowable, Table, TableStyle
from reportlab.lib.enums import TA_LEFT, TA_CENTER

PAGE_W, PAGE_H = A4
MARGIN = 2 * cm

ACCENT = colors.HexColor("#2C5F8A")
LIGHT_GRAY = colors.HexColor("#F5F5F5")
MID_GRAY = colors.HexColor("#666666")


def build_styles():
    styles = getSampleStyleSheet()
    return {
        "name": ParagraphStyle("name", fontSize=22, textColor=ACCENT, spaceAfter=6, spaceBefore=0, leading=28, fontName="Helvetica-Bold"),
        "title": ParagraphStyle("title", fontSize=13, textColor=MID_GRAY, spaceAfter=6, spaceBefore=0, leading=18, fontName="Helvetica"),
        "contact": ParagraphStyle("contact", fontSize=9, textColor=MID_GRAY, spaceAfter=4, leading=14, fontName="Helvetica"),
        "section": ParagraphStyle("section", fontSize=11, textColor=ACCENT, spaceBefore=10, spaceAfter=4,
                                   fontName="Helvetica-Bold", borderPad=2),
        "body": ParagraphStyle("body", fontSize=9.5, textColor=colors.black, spaceAfter=3,
                                fontName="Helvetica", leading=14),
        "bullet": ParagraphStyle("bullet", fontSize=9.5, textColor=colors.black, spaceAfter=2,
                                  fontName="Helvetica", leftIndent=14, leading=13, bulletIndent=4),
        "job_title": ParagraphStyle("job_title", fontSize=10, textColor=colors.black, spaceAfter=1,
                                     fontName="Helvetica-Bold"),
        "employer": ParagraphStyle("employer", fontSize=9.5, textColor=MID_GRAY, spaceAfter=1,
                                    fontName="Helvetica-Oblique"),
    }

def section_header(text, styles):
    return [
        Paragraph(text.upper(), styles["section"]),
        HRFlowable(width="100%", thickness=1, color=ACCENT, spaceAfter=4),
    ]

def bullet_item(text, styles):
    return Paragraph(f"• {text}", styles["bullet"])

def cv_header(name, job_title, contact_line, styles):
    return [
        Paragraph(name, styles["name"]),
        Paragraph(job_title, styles["title"]),
        Paragraph(contact_line, styles["contact"]),
        Spacer(1, 6),
    ]

# ─────────────────────────────────────────────
# CV 1: Alex Nguyen — Senior AI Engineer
# ─────────────────────────────────────────────
def build_alex(styles):
    story = []

    story += cv_header(
        "Alex Nguyen",
        "Senior AI / Machine Learning Engineer",
        "alex.nguyen@email.com  |  +1 (415) 882-3317  |  San Francisco, CA  |  "
        "linkedin.com/in/alex-nguyen-ml  |  github.com/alexnguyen-ai",
        styles)

    # Summary
    story += section_header("Professional Summary", styles)
    story.append(Paragraph(
        "Senior AI Engineer with 7+ years of experience designing and deploying production-grade machine learning "
        "systems. Deep expertise in large language models, retrieval-augmented generation (RAG), and MLOps pipelines. "
        "Led teams at top-tier AI companies delivering models that handle hundreds of millions of inference requests "
        "daily. Passionate about bridging research and engineering to ship impactful AI products.",
        styles["body"]))

    # Skills
    story += section_header("Technical Skills", styles)
    skills_data = [
        ["Languages", "Python (8 yrs), Scala (3 yrs), Go (2 yrs), SQL"],
        ["ML / AI Frameworks", "PyTorch (6 yrs), TensorFlow (4 yrs), HuggingFace Transformers (4 yrs), LangChain (3 yrs)"],
        ["LLM & GenAI", "OpenAI API, Anthropic Claude, RAG architectures, prompt engineering, fine-tuning (LoRA/QLoRA)"],
        ["MLOps", "MLflow (4 yrs), Weights & Biases, Ray (3 yrs), KubeFlow, Airflow"],
        ["Cloud & Infra", "AWS SageMaker (5 yrs), GCP Vertex AI (3 yrs), Kubernetes, Docker"],
        ["Databases", "PostgreSQL, Pinecone, Weaviate, Redis, BigQuery"],
    ]
    t = Table(skills_data, colWidths=[4.5 * cm, 13 * cm])
    t.setStyle(TableStyle([
        ("FONTNAME", (0, 0), (0, -1), "Helvetica-Bold"),
        ("FONTSIZE", (0, 0), (-1, -1), 9),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("ROWBACKGROUNDS", (0, 0), (-1, -1), [LIGHT_GRAY, colors.white]),
        ("TOPPADDING", (0, 0), (-1, -1), 4),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
        ("LEFTPADDING", (0, 0), (-1, -1), 6),
        ("GRID", (0, 0), (-1, -1), 0.3, colors.lightgrey),
    ]))
    story.append(t)
    story.append(Spacer(1, 6))

    # Work History
    story += section_header("Work History", styles)

    story.append(Paragraph("Lead AI Engineer", styles["job_title"]))
    story.append(Paragraph("Anthropic-backed AI Startup — San Francisco, CA  |  Jan 2021 – Present", styles["employer"]))
    for item in [
        "Architected a production RAG system handling 50M+ queries/month with sub-200ms p99 latency.",
        "Fine-tuned Llama 3 and Mistral models using QLoRA on domain-specific datasets, achieving 18% accuracy improvement over baseline.",
        "Built an automated model evaluation pipeline (MLflow + W&B) reducing release cycles from 2 weeks to 3 days.",
        "Led a team of 6 ML engineers; mentored 3 junior engineers to promotion.",
        "Tech stack: Python, PyTorch, LangChain, Pinecone, AWS SageMaker, Kubernetes.",
    ]:
        story.append(bullet_item(item, styles))
    story.append(Spacer(1, 6))

    story.append(Paragraph("Senior Machine Learning Engineer", styles["job_title"]))
    story.append(Paragraph("Palantir Technologies — New York, NY  |  Jun 2018 – Dec 2020", styles["employer"]))
    for item in [
        "Designed NLP pipelines for entity recognition and relationship extraction on government datasets.",
        "Reduced model inference costs by 40% via ONNX conversion and quantization.",
        "Integrated ML models into Palantir Foundry, enabling 30+ enterprise analysts to self-serve AI insights.",
        "Delivered computer vision pipeline for document classification with 94.7% F1 score.",
    ]:
        story.append(bullet_item(item, styles))
    story.append(Spacer(1, 6))

    story.append(Paragraph("Machine Learning Engineer", styles["job_title"]))
    story.append(Paragraph("LinkedIn — Sunnyvale, CA  |  Jul 2016 – May 2018", styles["employer"]))
    for item in [
        "Developed job recommendation models using collaborative filtering and content-based features, improving CTR by 12%.",
        "Built feature engineering pipelines processing 500GB/day of user interaction data in Spark.",
        "Shipped A/B testing framework for rapid ML experiment iteration.",
    ]:
        story.append(bullet_item(item, styles))
    story.append(Spacer(1, 6))

    # Education
    story += section_header("Education", styles)
    story.append(Paragraph("M.Sc. Computer Science — Specialisation: Machine Learning", styles["job_title"]))
    story.append(Paragraph("Stanford University, Stanford, CA  |  Completed 2016  |  GPA: 3.92 / 4.00", styles["employer"]))
    story.append(Spacer(1, 4))
    story.append(Paragraph("B.Sc. Computer Science", styles["job_title"]))
    story.append(Paragraph("UC Berkeley, Berkeley, CA  |  Completed 2014  |  GPA: 3.85 / 4.00", styles["employer"]))
    story.append(Spacer(1, 6))

    # Certifications
    story += section_header("Certifications", styles)
    for cert in [
        "AWS Certified Machine Learning – Specialty (2023)",
        "Google Professional Machine Learning Engineer (2022)",
        "Deep Learning Specialization — Coursera / deeplearning.ai (2020)",
        "Certified Kubernetes Administrator (CKA) (2021)",
    ]:
        story.append(bullet_item(cert, styles))
    story.append(Spacer(1, 6))

    # Projects
    story += section_header("Personal Projects", styles)
    for proj in [
        "OpenRAG: Open-source RAG toolkit (2.1k GitHub stars) — modular retrieval pipeline supporting Pinecone, Weaviate, pgvector.",
        "LLM Benchmark Suite: Evaluation harness comparing 15+ LLMs on reasoning, coding, and factuality tasks.",
        "DocIntel: AI document extraction service using multimodal LLM to parse PDFs, returning structured JSON output.",
    ]:
        story.append(bullet_item(proj, styles))
    story.append(Spacer(1, 6))

    # Writings
    story += section_header("Technical Writings", styles)
    for writing in [
        "\"Scaling RAG to 50M Queries/Month\" — published on Towards Data Science, 18k reads.",
        "\"QLoRA Fine-tuning in Production: Lessons Learned\" — personal blog, featured in TLDR AI newsletter.",
        "\"Vector Database Showdown: Pinecone vs Weaviate vs pgvector\" — Medium, 9k reads.",
    ]:
        story.append(bullet_item(writing, styles))

    return story


# ─────────────────────────────────────────────
# CV 2: Marcus Weber — Mid-Level Backend Developer
# ─────────────────────────────────────────────
def build_marcus(styles):
    story = []

    story += cv_header(
        "Marcus Weber",
        "Backend Software Engineer",
        "marcus.weber@email.de  |  +49 151 2341 8897  |  Berlin, Germany  |  "
        "linkedin.com/in/marcusweber-dev  |  github.com/mweber-backend",
        styles)

    # Summary
    story += section_header("Professional Summary", styles)
    story.append(Paragraph(
        "Backend Software Engineer with 4 years of professional experience building scalable, high-availability "
        "services in Java and Go. Solid grounding in microservice architecture, REST/gRPC APIs, and event-driven "
        "systems with Kafka. Experienced in cloud-native deployments on AWS and GCP. Committed to clean code, "
        "thorough testing, and continuous delivery practices.",
        styles["body"]))

    # Skills
    story += section_header("Technical Skills", styles)
    skills_data = [
        ["Languages", "Java (4 yrs), Go (2 yrs), Python (3 yrs), SQL, Bash"],
        ["Frameworks", "Spring Boot (4 yrs), Quarkus (1 yr), gRPC, REST"],
        ["Messaging", "Apache Kafka (3 yrs), RabbitMQ (2 yrs)"],
        ["Databases", "PostgreSQL (4 yrs), MySQL, Redis, MongoDB, Elasticsearch"],
        ["Cloud & DevOps", "AWS (ECS, RDS, S3, Lambda), Docker, Kubernetes, Terraform, GitHub Actions"],
        ["Testing", "JUnit 5, Testcontainers, Mockito, k6 (load testing)"],
        ["Architecture", "Microservices, DDD, CQRS, event-sourcing, API Gateway patterns"],
    ]
    t = Table(skills_data, colWidths=[4.5 * cm, 13 * cm])
    t.setStyle(TableStyle([
        ("FONTNAME", (0, 0), (0, -1), "Helvetica-Bold"),
        ("FONTSIZE", (0, 0), (-1, -1), 9),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("ROWBACKGROUNDS", (0, 0), (-1, -1), [LIGHT_GRAY, colors.white]),
        ("TOPPADDING", (0, 0), (-1, -1), 4),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
        ("LEFTPADDING", (0, 0), (-1, -1), 6),
        ("GRID", (0, 0), (-1, -1), 0.3, colors.lightgrey),
    ]))
    story.append(t)
    story.append(Spacer(1, 6))

    # Work History
    story += section_header("Work History", styles)

    story.append(Paragraph("Backend Engineer", styles["job_title"]))
    story.append(Paragraph("Zalando SE — Berlin, Germany  |  Mar 2022 – Present", styles["employer"]))
    for item in [
        "Developed and maintained order-processing microservices handling 200k+ transactions/day using Java 17 and Spring Boot.",
        "Migrated legacy monolith modules to event-driven microservices (Kafka), reducing average response time by 35%.",
        "Implemented gRPC-based internal APIs, cutting service-to-service latency by 28% vs. REST.",
        "Improved PostgreSQL query performance via index tuning and query plan analysis, reducing p99 from 800ms to 90ms.",
        "Set up Terraform infrastructure for 3 new services on AWS ECS with auto-scaling and blue/green deploys.",
    ]:
        story.append(bullet_item(item, styles))
    story.append(Spacer(1, 6))

    story.append(Paragraph("Junior Backend Developer", styles["job_title"]))
    story.append(Paragraph("FinTech Startup (Penta) — Berlin, Germany  |  Aug 2020 – Feb 2022", styles["employer"]))
    for item in [
        "Built REST APIs for a digital business banking platform (Spring Boot + PostgreSQL) serving 15,000 SME customers.",
        "Integrated third-party payment providers (SEPA, SWIFT) with proper error handling and idempotency.",
        "Wrote integration tests with Testcontainers, raising API test coverage from 42% to 78%.",
        "Participated in on-call rotation; reduced MTTR from 45 min to 12 min by improving runbooks and alerting.",
    ]:
        story.append(bullet_item(item, styles))
    story.append(Spacer(1, 6))

    # Education
    story += section_header("Education", styles)
    story.append(Paragraph("B.Sc. Applied Computer Science", styles["job_title"]))
    story.append(Paragraph("Technische Universität Berlin, Berlin, Germany  |  Completed 2020  |  Grade: 1.8 (German scale)", styles["employer"]))
    story.append(Spacer(1, 6))

    # Certifications
    story += section_header("Certifications", styles)
    for cert in [
        "AWS Certified Developer – Associate (2023)",
        "Oracle Certified Professional, Java SE 17 Developer (2022)",
        "Confluent Certified Developer for Apache Kafka (2023)",
        "Certified Kubernetes Application Developer (CKAD) (2023)",
    ]:
        story.append(bullet_item(cert, styles))
    story.append(Spacer(1, 6))

    # Projects
    story += section_header("Personal Projects", styles)
    for proj in [
        "BudgetTrackr: Personal finance REST API (Go + PostgreSQL) with JWT auth, deployed on fly.io — 320 GitHub stars.",
        "kafka-dead-letter-ui: Web dashboard for inspecting and replaying Kafka dead-letter queue messages — used internally at Penta.",
        "spring-boot-starter-audit: Spring Boot auto-configuration library for domain event auditing, published to Maven Central.",
    ]:
        story.append(bullet_item(proj, styles))
    story.append(Spacer(1, 6))

    # Writings
    story += section_header("Technical Writings", styles)
    for writing in [
        "\"Migrating a Monolith to Kafka-Driven Microservices — A Practical Guide\" — published on Baeldung.com.",
        "\"Zero-Downtime Postgres Migrations with Spring Boot\" — personal blog, 6k reads.",
        "\"gRPC vs REST: When to Use Which in 2024\" — Medium, 4.5k reads.",
    ]:
        story.append(bullet_item(writing, styles))

    return story


# ─────────────────────────────────────────────
# CV 3: Sofia Martinez — Mid-Level UX/UI Designer
# ─────────────────────────────────────────────
def build_sofia(styles):
    story = []

    story += cv_header(
        "Sofia Martinez",
        "UX / UI Product Designer",
        "sofia.martinez@design.io  |  +34 611 443 871  |  Barcelona, Spain  |  "
        "linkedin.com/in/sofia-martinez-ux  |  github.com/sofiamartinez-design",
        styles)

    # Summary
    story += section_header("Professional Summary", styles)
    story.append(Paragraph(
        "Product Designer with 4 years of experience crafting intuitive, accessible digital experiences for web and "
        "mobile applications. Skilled in user research, interaction design, and design systems. Collaborates closely "
        "with engineering and product teams to translate user needs into polished, high-conversion interfaces. "
        "Proficient in Figma, Adobe Creative Suite, and front-end prototyping with HTML/CSS.",
        styles["body"]))

    # Skills
    story += section_header("Technical Skills", styles)
    skills_data = [
        ["Design Tools", "Figma (4 yrs), Adobe XD (3 yrs), Sketch (2 yrs), InVision, Zeplin"],
        ["Prototyping", "Figma Prototyping, Framer, Principle, micro-interaction animation"],
        ["Research Methods", "User interviews, usability testing, A/B testing, card sorting, heatmaps (Hotjar, FullStory)"],
        ["Frontend", "HTML5, CSS3 / SCSS, Tailwind CSS, JavaScript (basic), React (familiar)"],
        ["Design Systems", "Design token architecture, component libraries, accessibility (WCAG 2.1 AA)"],
        ["Collaboration", "Jira, Confluence, Notion, GitHub, Storybook, Zeroheight"],
    ]
    t = Table(skills_data, colWidths=[4.5 * cm, 13 * cm])
    t.setStyle(TableStyle([
        ("FONTNAME", (0, 0), (0, -1), "Helvetica-Bold"),
        ("FONTSIZE", (0, 0), (-1, -1), 9),
        ("VALIGN", (0, 0), (-1, -1), "TOP"),
        ("ROWBACKGROUNDS", (0, 0), (-1, -1), [LIGHT_GRAY, colors.white]),
        ("TOPPADDING", (0, 0), (-1, -1), 4),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 4),
        ("LEFTPADDING", (0, 0), (-1, -1), 6),
        ("GRID", (0, 0), (-1, -1), 0.3, colors.lightgrey),
    ]))
    story.append(t)
    story.append(Spacer(1, 6))

    # Work History
    story += section_header("Work History", styles)

    story.append(Paragraph("Product Designer (UX/UI)", styles["job_title"]))
    story.append(Paragraph("Typeform — Barcelona, Spain  |  Feb 2022 – Present", styles["employer"]))
    for item in [
        "Led redesign of the form builder experience (Figma), reducing time-to-first-form from 8 min to 3.5 min (43% improvement).",
        "Built and maintained Typeform's component library (200+ components) in Figma and Storybook, used by 12 designers and 30 engineers.",
        "Conducted 40+ user interviews and 15 usability studies, synthesising insights to drive quarterly OKR-aligned roadmap decisions.",
        "Improved onboarding conversion by 22% through A/B-tested redesign of the sign-up and empty-state flows.",
        "Ensured WCAG 2.1 AA compliance across all new features; introduced colour-contrast audits into the design review process.",
    ]:
        story.append(bullet_item(item, styles))
    story.append(Spacer(1, 6))

    story.append(Paragraph("Junior UX Designer", styles["job_title"]))
    story.append(Paragraph("Evercom Digital Agency — Madrid, Spain  |  Sep 2020 – Jan 2022", styles["employer"]))
    for item in [
        "Designed responsive web interfaces for 8 client projects across e-commerce, fintech, and healthcare sectors.",
        "Produced wireframes, user flows, and high-fidelity prototypes using Figma and Adobe XD.",
        "Facilitated remote design sprints (Miro) delivering MVP concepts within 5-day timeboxes.",
        "Created brand-aligned design systems for 3 clients, reducing front-end development time by 30%.",
    ]:
        story.append(bullet_item(item, styles))
    story.append(Spacer(1, 6))

    # Education
    story += section_header("Education", styles)
    story.append(Paragraph("B.A. Graphic Design & Visual Communication", styles["job_title"]))
    story.append(Paragraph("Escola Superior de Disseny (ESDI), Barcelona, Spain  |  Completed 2020  |  Score: 8.9 / 10.0", styles["employer"]))
    story.append(Spacer(1, 4))
    story.append(Paragraph("Diploma in UX Design", styles["job_title"]))
    story.append(Paragraph("Ironhack Bootcamp, Madrid, Spain  |  Completed 2020  |  Pass with Distinction", styles["employer"]))
    story.append(Spacer(1, 6))

    # Certifications
    story += section_header("Certifications", styles)
    for cert in [
        "Google UX Design Certificate — Coursera (2021)",
        "Interaction Design Foundation: UX Research (2022)",
        "Figma Advanced Prototyping & Dev Mode Certification (2023)",
        "Nielsen Norman Group UX Certificate — UX Research (2023)",
    ]:
        story.append(bullet_item(cert, styles))
    story.append(Spacer(1, 6))

    # Projects
    story += section_header("Personal Projects", styles)
    for proj in [
        "AccessKit: Open-source Figma plugin that checks designs for WCAG 2.1 AA compliance — 800+ installs on Figma Community.",
        "PeoplePulse: UX case study — end-to-end design of an HR analytics dashboard, documented with research, wireframes, and prototype.",
        "Design Tokens Generator: CLI tool (Node.js) to export Figma variables to CSS/SCSS/JSON design tokens.",
    ]:
        story.append(bullet_item(proj, styles))
    story.append(Spacer(1, 6))

    # Writings
    story += section_header("Technical Writings", styles)
    for writing in [
        "\"How We Reduced Form Abandonment by 40% with Progressive Disclosure\" — Typeform Design Blog, featured on UX Collective.",
        "\"Building a Design System That Engineers Actually Use\" — Medium UX Collective, 11k reads.",
        "\"5 Figma Tricks That Will Save You Hours Every Week\" — personal blog, 7k reads.",
    ]:
        story.append(bullet_item(writing, styles))

    return story


# ─────────────────────────────────────────────
# Build PDFs
# ─────────────────────────────────────────────
OUTPUT_DIR = "."
CVDATA = [
    ("alex-nguyen-ai-engineer.pdf",        build_alex),
    ("marcus-weber-backend-developer.pdf", build_marcus),
    ("sofia-martinez-ux-designer.pdf",     build_sofia),
]

styles = build_styles()

for filename, builder_fn in CVDATA:
    path = f"{OUTPUT_DIR}/{filename}"
    doc = SimpleDocTemplate(
        path,
        pagesize=A4,
        leftMargin=MARGIN, rightMargin=MARGIN,
        topMargin=MARGIN, bottomMargin=MARGIN,
    )
    doc.build(builder_fn(styles))
    print(f"Created: {path}")

print("Done.")
