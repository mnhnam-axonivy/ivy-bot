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

---

## Chatbot (Employee Workflow) — Business Walkthrough

The **My Chatbot** screen lets employees check their own information and submit leave requests through a natural-language conversation powered by an HR AI Agent.

---

### Step 1 — Create Demo Data

Before using the chatbot for the first time, seed the employee database by running the **Create demo data** process. This creates two sample users:

| Username | Full name | Role |
|---|---|---|
| `employee` | John Employee | Employee (IT / Software Engineer) |
| `manager` | Jane Manager | Manager (HR / HR Manager) |

This step is idempotent — running it again when data already exists does nothing.

---

### Step 2 — Log in as an employee

Log in to the portal with:

- **Username:** `employee`
- **Password:** `employee`

---

### Step 3 — Open My Chatbot

Start the **My Chatbot** process. A conversational chat interface opens in the browser.

---

### Step 4 — Ask the chatbot

Type a message in plain language. The AI Agent knows who you are and can:

- **Look up your employee profile** — job title, department, manager, contact details
  > *"What is my current position?"*

- **Submit a leave request on your behalf** — just describe what you need
  > *"I'd like to take a vacation from May 5 to May 9."*
  > *"I need sick leave tomorrow."*

  Supported leave types: Vacation, Sick Leave, Personal, Parental, Bereavement.

The agent asks for any missing details (dates, reason) before submitting, so you can have a natural back-and-forth conversation.