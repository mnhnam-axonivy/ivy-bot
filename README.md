# Ivy Bot — Smart Workflow

> **Note:** Currently please use the `smart-workflow` and `smart-workflow-openai` modules in this repository. An official beta release is coming soon.

---

## Candidate Management — Business Walkthrough

The **Candidate Management** screen lets recruiters drop a CV and instantly get a structured candidate profile — no manual data entry required.

---

### Step 1 — Open the screen

Start the process  **Candidate Management**. You land on the **CV Screening — Talent Management** page.

---

### Step 2 — Upload a CV

Three sample CVs are available in the `demo_doc/` folder to try right away:

| Candidate | Role |
|---|---|
| Alex Nguyen | Senior AI / ML Engineer |
| Marcus Weber | Backend Developer |
| Sofia Martinez | UX Designer |

On the left panel, either **drag and drop** a PDF onto the upload zone or click **Select CV File** and choose one of the sample PDFs. The upload and AI parsing start automatically — a brief loading indicator shows while the CV is being processed.

---

### Step 3 — Review the candidate list

Once parsing completes, the candidate appears in the **Candidate Profiles** table on the right:

- Profile photo (extracted from the CV) or an initials avatar
- Full name, email address, and seniority level (Entry / Mid / Senior)
- Top skills at a glance

Repeat the upload for as many CVs as needed. Each one is added as a new row.

---

### Step 4 — Open a candidate's full profile

Click any row to open the **Candidate Details** view. The AI has already filled in:

- Contact details — phone, location, LinkedIn, GitHub
- AI-generated summary highlighting key strengths
- Skill breakdown with proficiency scores and months of experience per technology
- Full work history — employer, role, dates, responsibilities, and notable achievements
- Education, certifications, personal projects, and publications