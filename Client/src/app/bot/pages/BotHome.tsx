import { useState } from "react";
import { useNavigate } from "react-router";
import {
  Sparkles,
  Briefcase,
  Gauge,
  ListChecks,
  ArrowRight,
} from "lucide-react";
import Select from "react-select";
import { generateQuestions } from "../../../services/interview";
import ErrorBanner from "../components/ErrorBanner";
import LoadingSpinner from "../components/LoadingSpinner";
import BotLayout from "../components/BotLayout";

const JOB_ROLE_OPTIONS = [
  // Software Development
  { value: "Software Engineer", label: "Software Engineer" },
  { value: "Frontend Developer", label: "Frontend Developer" },
  { value: "Backend Developer", label: "Backend Developer" },
  { value: "Full Stack Developer", label: "Full Stack Developer" },

  // JavaScript
  { value: "React Developer", label: "React Developer" },
  { value: "Angular Developer", label: "Angular Developer" },
  { value: "Vue.js Developer", label: "Vue.js Developer" },
  { value: "Node.js Developer", label: "Node.js Developer" },
  { value: "Next.js Developer", label: "Next.js Developer" },

  // Java
  { value: "Java Developer", label: "Java Developer" },
  { value: "Spring Boot Developer", label: "Spring Boot Developer" },

  // Python
  { value: "Python Developer", label: "Python Developer" },
  { value: "Django Developer", label: "Django Developer" },
  { value: "Flask Developer", label: "Flask Developer" },

  // .NET
  { value: ".NET Developer", label: ".NET Developer" },
  { value: "C# Developer", label: "C# Developer" },
  { value: "ASP.NET Developer", label: "ASP.NET Developer" },

  // PHP
  { value: "PHP Developer", label: "PHP Developer" },
  { value: "Laravel Developer", label: "Laravel Developer" },

  // Mobile
  { value: "Android Developer", label: "Android Developer" },
  { value: "iOS Developer", label: "iOS Developer" },
  { value: "Flutter Developer", label: "Flutter Developer" },
  { value: "React Native Developer", label: "React Native Developer" },

  // DevOps & Cloud
  { value: "DevOps Engineer", label: "DevOps Engineer" },
  { value: "Cloud Engineer", label: "Cloud Engineer" },
  { value: "AWS Engineer", label: "AWS Engineer" },
  { value: "Azure Engineer", label: "Azure Engineer" },
  { value: "Google Cloud Engineer", label: "Google Cloud Engineer" },
  { value: "Site Reliability Engineer", label: "Site Reliability Engineer" },

  // Data
  { value: "Data Analyst", label: "Data Analyst" },
  { value: "Business Analyst", label: "Business Analyst" },
  { value: "Data Engineer", label: "Data Engineer" },
  { value: "Data Scientist", label: "Data Scientist" },
  { value: "Machine Learning Engineer", label: "Machine Learning Engineer" },
  { value: "AI Engineer", label: "AI Engineer" },

  // Testing
  { value: "QA Engineer", label: "QA Engineer" },
  { value: "Manual Tester", label: "Manual Tester" },
  { value: "Automation Test Engineer", label: "Automation Test Engineer" },
  { value: "SDET", label: "SDET" },

  // Database
  { value: "Database Administrator", label: "Database Administrator" },
  { value: "SQL Developer", label: "SQL Developer" },

  // Security
  { value: "Cyber Security Engineer", label: "Cyber Security Engineer" },
  {
    value: "Information Security Analyst",
    label: "Information Security Analyst",
  },
  { value: "Penetration Tester", label: "Penetration Tester" },

  // Design
  { value: "UI Designer", label: "UI Designer" },
  { value: "UX Designer", label: "UX Designer" },
  { value: "UI/UX Designer", label: "UI/UX Designer" },

  // Management
  { value: "Technical Lead", label: "Technical Lead" },
  { value: "Engineering Manager", label: "Engineering Manager" },
  { value: "Product Manager", label: "Product Manager" },
  { value: "Project Manager", label: "Project Manager" },

  // Other
  { value: "Blockchain Developer", label: "Blockchain Developer" },
  { value: "Embedded Systems Engineer", label: "Embedded Systems Engineer" },
  { value: "Game Developer", label: "Game Developer" },
  { value: "Network Engineer", label: "Network Engineer" },
  { value: "System Administrator", label: "System Administrator" },
];

const DIFFICULTIES = ["Easy", "Medium", "Hard"];
const EXPERIENCE_LEVELS = [
  "0-1 Years",
  "1-3 Years",
  "3-5 Years",
  "5-8 Years",
  "8+ Years",
];
const QUESTION_COUNTS = [5, 10, 15];

interface FormState {
  role: string;
  experience: string;
  difficulty: string;
  questionCount: number;
}

export default function BotHome() {
  const navigate = useNavigate();

  const [form, setForm] = useState<FormState>({
    role: "",
    experience: EXPERIENCE_LEVELS[1],
    difficulty: "Medium",
    questionCount: 10,
  });

  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    const { name, value } = e.target;

    setForm({
      ...form,
      [name]: name === "questionCount" ? Number(value) : value,
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");

    if (!form.role) {
      setError("Please enter a job role.");
      return;
    }

    setLoading(true);

    try {
      // ✅ NO USER ID - JWT handles identity
      const questions = await generateQuestions(form);

      navigate("/interview", {
        state: { ...form, questions },
      });
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <BotLayout title="AI Mock Interview" subtitle="Preparing your session">
        <LoadingSpinner label="Generating your interview questions..." />
      </BotLayout>
    );
  }

  return (
    <BotLayout
      title="AI Mock Interview"
      subtitle="Practice with an AI interviewer tailored to your role"
    >
      {/* Hero */}
      <div className="mb-8 text-center">
        <div className="mx-auto mb-4 inline-flex items-center gap-1.5 rounded-full border border-[#00bfa6]/25 bg-[#00bfa6]/10 px-3 py-1 text-xs font-semibold text-[#00966f]">
          <Sparkles className="h-3.5 w-3.5" />
          AI-Powered Session
        </div>
        <h1 className="text-3xl font-semibold tracking-tight text-slate-900 sm:text-4xl">
          Start your mock interview
        </h1>
        <p className="mx-auto mt-2 max-w-md text-sm text-slate-500">
          Tell us what you're preparing for — we'll generate tailored questions
          and guide you through a live-feeling session.
        </p>
      </div>

      {/* Glass card */}
      <div className="mx-auto max-w-xl rounded-2xl border border-white/60 bg-white/70 p-6 shadow-[0_8px_40px_-12px_rgba(13,27,42,0.15)] backdrop-blur-xl sm:p-8">
        {error && (
          <div className="mb-5">
            <ErrorBanner message={error} onRetry={handleSubmit as any} />
          </div>
        )}

        <form onSubmit={handleSubmit} className="flex flex-col gap-5">
          <div>
            <label className="mb-1.5 flex items-center gap-1.5 text-sm font-medium text-slate-700">
              <Briefcase className="h-3.5 w-3.5 text-slate-400" />
              Job Role
            </label>
            
            <Select
              options={JOB_ROLE_OPTIONS}
              placeholder="Select or search a job role..."
              value={JOB_ROLE_OPTIONS.find(
                (option) => option.value === form.role,
              )}
              onChange={(selectedOption) =>
                setForm({
                  ...form,
                  role: selectedOption?.value || "",
                })
              }
              styles={{
                control: (base, state) => ({
                  ...base,
                  minHeight: "46px",
                  borderRadius: "12px",
                  borderColor: state.isFocused ? "#00bfa6" : "#e2e8f0",
                  boxShadow: state.isFocused
                    ? "0 0 0 2px rgba(0,191,166,0.2)"
                    : "none",
                  "&:hover": {
                    borderColor: "#00bfa6",
                  },
                }),
                option: (base, state) => ({
                  ...base,
                  backgroundColor: state.isSelected
                    ? "#00bfa6"
                    : state.isFocused
                      ? "#f0fdfa"
                      : "#fff",
                  color: state.isSelected ? "#fff" : "#0f172a",
                  cursor: "pointer",
                }),
                placeholder: (base) => ({
                  ...base,
                  color: "#94a3b8",
                }),
                singleValue: (base) => ({
                  ...base,
                  color: "#0f172a",
                  fontWeight: 350,
                }),
                menu: (base) => ({
                  ...base,
                  borderRadius: "12px",
                  overflow: "hidden",
                }),
              }}
            />
          </div>

          <div>
            <label className="mb-1.5 block text-sm font-medium text-slate-700">
              Experience Level
            </label>
            <select
              name="experience"
              value={form.experience}
              onChange={handleChange}
              className="w-full rounded-xl border border-slate-200 bg-white px-3.5 py-2.5 text-sm text-slate-800 shadow-sm outline-none transition-colors focus:border-[#00bfa6] focus:ring-2 focus:ring-[#00bfa6]/20"
            >
              {EXPERIENCE_LEVELS.map((level) => (
                <option key={level} value={level}>
                  {level}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="mb-1.5 flex items-center gap-1.5 text-sm font-medium text-slate-700">
              <Gauge className="h-3.5 w-3.5 text-slate-400" />
              Difficulty
            </label>

            <div className="flex gap-2">
              {DIFFICULTIES.map((d) => (
                <button
                  key={d}
                  type="button"
                  onClick={() => setForm({ ...form, difficulty: d })}
                  className={`flex-1 rounded-xl border px-3 py-2.5 text-sm font-medium transition-all ${
                    form.difficulty === d
                      ? "border-[#00bfa6] bg-[#00bfa6]/10 text-[#00966f] shadow-sm"
                      : "border-slate-200 bg-white text-slate-500 hover:border-slate-300 hover:bg-slate-50"
                  }`}
                >
                  {d}
                </button>
              ))}
            </div>
          </div>

          <div>
            <label className="mb-1.5 flex items-center gap-1.5 text-sm font-medium text-slate-700">
              <ListChecks className="h-3.5 w-3.5 text-slate-400" />
              Number of Questions
            </label>

            <div className="flex gap-2">
              {QUESTION_COUNTS.map((count) => (
                <button
                  key={count}
                  type="button"
                  onClick={() => setForm({ ...form, questionCount: count })}
                  className={`flex-1 rounded-xl border px-3 py-2.5 text-sm font-medium transition-all ${
                    form.questionCount === count
                      ? "border-[#00bfa6] bg-[#00bfa6]/10 text-[#00966f] shadow-sm"
                      : "border-slate-200 bg-white text-slate-500 hover:border-slate-300 hover:bg-slate-50"
                  }`}
                >
                  {count}
                </button>
              ))}
            </div>
          </div>

          <button
            type="submit"
            className="mt-2 flex items-center justify-center gap-2 rounded-xl bg-[#0d1b2a] px-4 py-3 text-sm font-semibold text-white shadow-lg shadow-[#0d1b2a]/15 transition-all hover:bg-[#132234] active:scale-[0.99]"
          >
            Start Interview
            <ArrowRight className="h-4 w-4" />
          </button>
        </form>
      </div>
    </BotLayout>
  );
}
