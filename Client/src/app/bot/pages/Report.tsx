import { useLocation, useNavigate, useParams } from 'react-router'
import { useEffect, useState } from 'react'
import {
  ChevronDown,
  MessageSquare,
  Sparkles,
  ThumbsDown,
  ThumbsUp,
  TrendingUp,
} from 'lucide-react'
import { getInterviewById } from '../../../services/history'
import LoadingSpinner from '../components/LoadingSpinner'
import ErrorBanner from '../components/ErrorBanner'
import SpeakerButton from '../components/SpeakerButton'
import BotLayout from '../components/BotLayout'

interface ListSectionProps {
  title: string
  items?: string[]
  tone: 'good' | 'bad' | 'neutral'
}

interface QuestionAnalysisItem {
  question: string
  answer: string
  feedback: string
  score: number
}

interface Evaluation {
  overallScore: number
  technicalScore: number
  communicationScore: number
  problemSolvingScore: number
  confidenceScore: number
  strengths: string[]
  weaknesses: string[]
  improvements: string[]
  questionAnalysis: QuestionAnalysisItem[]
  hiringRecommendation: string
}

interface InterviewData {
  evaluation: Evaluation
  role: string
}

const SCORE_METRICS: { key: keyof Evaluation; label: string }[] = [
  { key: 'technicalScore', label: 'Technical' },
  { key: 'communicationScore', label: 'Communication' },
  { key: 'problemSolvingScore', label: 'Problem Solving' },
  { key: 'confidenceScore', label: 'Confidence' },
]

function scoreColor(score: number) {
  if (score >= 80) return '#00bfa6'
  if (score >= 50) return '#f5a623'
  return '#f85149'
}

function OverallScoreRing({ score }: { score: number }) {
  const radius = 54
  const circumference = 2 * Math.PI * radius
  const offset = circumference - (Math.min(Math.max(score, 0), 100) / 100) * circumference
  const color = scoreColor(score)

  return (
    <div className="relative flex h-36 w-36 items-center justify-center">
      <svg viewBox="0 0 130 130" className="h-36 w-36 -rotate-90">
        <circle
          cx="65"
          cy="65"
          r={radius}
          fill="none"
          stroke="#e6ecf2"
          strokeWidth="11"
        />
        <circle
          cx="65"
          cy="65"
          r={radius}
          fill="none"
          stroke={color}
          strokeWidth="11"
          strokeLinecap="round"
          strokeDasharray={circumference}
          strokeDashoffset={offset}
          style={{ transition: 'stroke-dashoffset 0.8s ease-out' }}
        />
      </svg>
      <div className="absolute flex flex-col items-center">
        <span className="text-3xl font-semibold text-slate-800">{score}</span>
        <span className="text-[11px] font-medium text-slate-400">/ 100</span>
      </div>
    </div>
  )
}

function MetricBar({ label, value }: { label: string; value: number }) {
  const color = scoreColor(value)
  return (
    <div>
      <div className="mb-1.5 flex items-center justify-between text-xs">
        <span className="font-medium text-slate-600">{label}</span>
        <span className="font-semibold text-slate-800">{value}</span>
      </div>
      <div className="h-2 w-full overflow-hidden rounded-full bg-slate-100">
        <div
          className="h-full rounded-full transition-all duration-700 ease-out"
          style={{ width: `${value}%`, backgroundColor: color }}
        />
      </div>
    </div>
  )
}

function ListSection({ title, items, tone }: ListSectionProps) {
  if (!items?.length) return null

  const toneStyles = {
    good: {
      wrap: 'border-emerald-200 bg-emerald-50/70',
      title: 'text-emerald-800',
      icon: <ThumbsUp className="h-4 w-4 text-emerald-600" />,
      dot: 'bg-emerald-500',
    },
    bad: {
      wrap: 'border-amber-200 bg-amber-50/70',
      title: 'text-amber-800',
      icon: <ThumbsDown className="h-4 w-4 text-amber-600" />,
      dot: 'bg-amber-500',
    },
    neutral: {
      wrap: 'border-blue-200 bg-blue-50/70',
      title: 'text-blue-800',
      icon: <TrendingUp className="h-4 w-4 text-blue-600" />,
      dot: 'bg-blue-500',
    },
  }[tone]

  const readAloudText = `${title}. ${items.join('. ')}`

  return (
    <div className={`rounded-2xl border p-4 backdrop-blur-xl ${toneStyles.wrap}`}>
      <div className="mb-3 flex items-center justify-between gap-2">
        <h3 className={`flex items-center gap-2 text-sm font-semibold ${toneStyles.title}`}>
          {toneStyles.icon}
          {title}
        </h3>
        <SpeakerButton text={readAloudText} />
      </div>
      <ul className="space-y-2 text-sm text-slate-700">
        {items.map((item, idx) => (
          <li key={idx} className="flex items-start gap-2">
            <span className={`mt-1.5 h-1.5 w-1.5 shrink-0 rounded-full ${toneStyles.dot}`} />
            {item}
          </li>
        ))}
      </ul>
    </div>
  )
}

function QuestionAnalysisBlock({
  qa,
  index,
  isOpen,
  onToggle,
}: {
  qa: QuestionAnalysisItem
  index: number
  isOpen: boolean
  onToggle: () => void
}) {
  const color = scoreColor(qa.score)

  return (
    <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white/80 backdrop-blur-xl">
      <button
        type="button"
        onClick={onToggle}
        className="flex w-full items-center justify-between gap-3 px-4 py-3.5 text-left"
      >
        <div className="flex min-w-0 items-center gap-3">
          <span
            className="flex h-7 w-7 shrink-0 items-center justify-center rounded-full text-xs font-semibold text-white"
            style={{ backgroundColor: color }}
          >
            {qa.score}
          </span>
          <span className="truncate text-sm font-medium text-slate-700">
            Q{index + 1}: {qa.question}
          </span>
        </div>
        <ChevronDown
          className={`h-4 w-4 shrink-0 text-slate-400 transition-transform ${
            isOpen ? 'rotate-180' : ''
          }`}
        />
      </button>

      {isOpen && (
        <div className="space-y-3 border-t border-slate-100 px-4 py-4">
          <div className="flex items-start gap-2.5">
            <div className="mt-0.5 flex h-7 w-7 shrink-0 items-center justify-center rounded-full bg-slate-200">
              <span className="text-[10px] font-semibold text-slate-500">YOU</span>
            </div>
            <div className="rounded-xl rounded-tl-sm bg-slate-100 px-3.5 py-2.5 text-sm text-slate-700">
              {qa.answer || <span className="italic text-slate-400">No answer provided</span>}
            </div>
          </div>

          <div className="flex items-start gap-2.5">
            <div className="mt-0.5 flex h-7 w-7 shrink-0 items-center justify-center rounded-full bg-[#0d1b2a]">
              <Sparkles className="h-3.5 w-3.5 text-[#00bfa6]" />
            </div>
            <div className="flex-1 rounded-xl rounded-tl-sm bg-[#00bfa6]/10 px-3.5 py-2.5 text-sm text-[#0a5c4f]">
              <div className="flex items-start justify-between gap-2">
                <span>{qa.feedback}</span>
                <SpeakerButton text={qa.feedback} className="shrink-0" />
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  )
}

export default function Report() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()

  const location = useLocation() as {
    state?: InterviewData
  }

  const [evaluation, setEvaluation] = useState<Evaluation | null>(
    location.state?.evaluation ?? null
  )

  const [role, setRole] = useState<string>(location.state?.role ?? '')
  const [loading, setLoading] = useState<boolean>(!location.state?.evaluation && !!id)
  const [error, setError] = useState<string>('')
  const [openIndex, setOpenIndex] = useState<number | null>(0)

  useEffect(() => {
    if (!id || evaluation) return

    const fetchReport = async () => {
      try {
        setLoading(true)
        const data: InterviewData = await getInterviewById(id)

        setEvaluation(data.evaluation)
        setRole(data.role)
      } catch (err: any) {
        setError(err.message || 'Failed to load report')
      } finally {
        setLoading(false)
      }
    }

    fetchReport()
  }, [id, evaluation])

  if (loading) {
    return (
      <BotLayout title="Interview Report">
        <LoadingSpinner label="Loading report..." />
      </BotLayout>
    )
  }

  if (error) {
    return (
      <BotLayout title="Interview Report">
        <div className="mx-auto mt-10 max-w-2xl">
          <ErrorBanner message={error} />
        </div>
      </BotLayout>
    )
  }

  if (!evaluation) {
    return (
      <BotLayout title="Interview Report">
        <div className="mx-auto mt-16 max-w-md text-center">
          <p className="mb-4 text-slate-600">No report available.</p>
          <button
            onClick={() => navigate('/ai-mock')}
            className="rounded-xl bg-[#0d1b2a] px-4 py-2.5 text-sm font-medium text-white hover:bg-[#132234]"
          >
            Start a new interview
          </button>
        </div>
      </BotLayout>
    )
  }

  const {
    overallScore,
    strengths,
    weaknesses,
    improvements,
    questionAnalysis,
    hiringRecommendation,
  } = evaluation

  const recommendationTone = /strong|hire|yes/i.test(hiringRecommendation)
    ? 'good'
    : /no|reject|not/i.test(hiringRecommendation)
    ? 'bad'
    : 'neutral'

  const recommendationClasses = {
    good: 'border-emerald-200 bg-emerald-50 text-emerald-800',
    bad: 'border-red-200 bg-red-50 text-red-800',
    neutral: 'border-blue-200 bg-blue-50 text-blue-800',
  }[recommendationTone]

  const fullFeedbackReadAloud = [
    `Overall score ${overallScore} out of 100.`,
    strengths?.length ? `Strengths: ${strengths.join('. ')}.` : '',
    weaknesses?.length ? `Weaknesses: ${weaknesses.join('. ')}.` : '',
    improvements?.length ? `Suggested improvements: ${improvements.join('. ')}.` : '',
    `Hiring recommendation: ${hiringRecommendation}.`,
  ]
    .filter(Boolean)
    .join(' ')

  return (
    <BotLayout title="Interview Report" subtitle={role || undefined}>
      <div className="pb-8">
        {/* Header */}
        <div className="mb-6 flex flex-col items-center justify-between gap-4 sm:flex-row">
          <div className="text-center sm:text-left">
            <h1 className="text-2xl font-semibold tracking-tight text-slate-900">
              Interview Report
            </h1>
            {role && <p className="text-sm text-slate-500">{role}</p>}
          </div>
          <SpeakerButton text={fullFeedbackReadAloud} size="md" />
        </div>

        {/* Score panel */}
        <div className="mb-6 grid grid-cols-1 gap-4 rounded-2xl border border-white/60 bg-white/80 p-6 shadow-sm backdrop-blur-xl md:grid-cols-[auto_1fr]">
          <div className="flex flex-col items-center justify-center gap-2 md:border-r md:border-slate-100 md:pr-6">
            <OverallScoreRing score={overallScore} />
            <span className="text-xs font-medium text-slate-500">Overall Score</span>
          </div>

          <div className="flex flex-col justify-center gap-4">
            {SCORE_METRICS.map(({ key, label }) => (
              <MetricBar key={key} label={label} value={Number(evaluation[key])} />
            ))}
          </div>
        </div>

        {/* Recommendation banner */}
        <div
          className={`mb-6 flex items-center justify-center gap-2 rounded-2xl border px-5 py-4 text-center ${recommendationClasses}`}
        >
          <span className="text-sm">Hiring Recommendation:</span>
          <span className="text-sm font-semibold">{hiringRecommendation}</span>
        </div>

        {/* Insight cards */}
        <div className="mb-6 grid gap-4 sm:grid-cols-3">
          <ListSection title="Strengths" items={strengths} tone="good" />
          <ListSection title="Weaknesses" items={weaknesses} tone="bad" />
          <ListSection title="Improvements" items={improvements} tone="neutral" />
        </div>

        {/* Question-by-question analysis */}
        {questionAnalysis?.length > 0 && (
          <div>
            <h3 className="mb-3 flex items-center gap-2 text-sm font-semibold text-slate-800">
              <MessageSquare className="h-4 w-4 text-slate-500" />
              Question-by-Question Analysis
            </h3>

            <div className="flex flex-col gap-3">
              {questionAnalysis.map((qa, idx) => (
                <QuestionAnalysisBlock
                  key={idx}
                  qa={qa}
                  index={idx}
                  isOpen={openIndex === idx}
                  onToggle={() => setOpenIndex(openIndex === idx ? null : idx)}
                />
              ))}
            </div>
          </div>
        )}

        <div className="mt-8 flex justify-center gap-3">
          <button
            onClick={() => navigate('/ai-mock')}
            className="rounded-xl bg-[#0d1b2a] px-5 py-2.5 text-sm font-semibold text-white shadow-lg shadow-[#0d1b2a]/15 transition-all hover:bg-[#132234]"
          >
            New Interview
          </button>

          <button
            onClick={() => navigate('/history')}
            className="rounded-xl border border-slate-200 bg-white px-5 py-2.5 text-sm font-medium text-slate-600 shadow-sm transition-all hover:bg-slate-50"
          >
            View History
          </button>
        </div>
      </div>
    </BotLayout>
  )
}
