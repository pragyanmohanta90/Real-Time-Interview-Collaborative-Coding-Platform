import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router'
import { Calendar, ChevronRight, Inbox, Sparkles } from 'lucide-react'
import { getHistory } from '../../../services/history'
import LoadingSpinner from '../components/LoadingSpinner'
import ErrorBanner from '../components/ErrorBanner'
import BotLayout from '../components/BotLayout'

interface Interview {
  id: string
  role: string
  difficulty: string
  score: number
  createdAt?: string
}

function scoreBadgeClasses(score: number) {
  if (score >= 80) return 'bg-emerald-50 text-emerald-700 border-emerald-200'
  if (score >= 50) return 'bg-amber-50 text-amber-700 border-amber-200'
  return 'bg-red-50 text-red-700 border-red-200'
}

function formatDate(value?: string) {
  if (!value) return '—'
  const d = new Date(value)
  if (Number.isNaN(d.getTime())) return value
  return d.toLocaleDateString(undefined, {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
  })
}

export default function History() {
  const navigate = useNavigate()

  const [interviews, setInterviews] = useState<Interview[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const fetchHistory = async () => {
    setLoading(true)
    setError('')

    try {
      const data = await getHistory()
      setInterviews(data)
    } catch (err: any) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    fetchHistory()
  }, [])

  if (loading) {
    return (
      <BotLayout title="History" subtitle="Your past mock interviews">
        <LoadingSpinner label="Loading your interview history..." />
      </BotLayout>
    )
  }

  return (
    <BotLayout title="History" subtitle="Your past mock interviews">
      {error && (
        <div className="mb-5">
          <ErrorBanner message={error} onRetry={fetchHistory} />
        </div>
      )}

      {!error && interviews.length === 0 ? (
        <div className="mx-auto mt-10 flex max-w-md flex-col items-center rounded-2xl border border-white/60 bg-white/70 px-8 py-14 text-center shadow-sm backdrop-blur-xl">
          <div className="mb-4 flex h-12 w-12 items-center justify-center rounded-full bg-[#00bfa6]/10">
            <Inbox className="h-5 w-5 text-[#00bfa6]" />
          </div>
          <p className="text-sm font-medium text-slate-700">No interviews yet</p>
          <p className="mt-1 text-sm text-slate-500">
            Complete your first mock interview to see it here.
          </p>
          <button
            onClick={() => navigate('/ai-mock')}
            className="mt-5 flex items-center gap-2 rounded-xl bg-[#0d1b2a] px-4 py-2.5 text-sm font-semibold text-white transition-all hover:bg-[#132234]"
          >
            <Sparkles className="h-4 w-4" />
            Start an interview
          </button>
        </div>
      ) : (
        <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3">
          {interviews.map((item) => (
            <div
              key={item.id}
              className="group flex flex-col justify-between rounded-2xl border border-white/60 bg-white/80 p-5 shadow-sm backdrop-blur-xl transition-all duration-200 hover:-translate-y-0.5 hover:shadow-lg hover:shadow-slate-900/5"
            >
              <div>
                <div className="mb-3 flex items-start justify-between gap-3">
                  <h3 className="text-sm font-semibold text-slate-800">
                    {item.role}
                  </h3>
                  <span
                    className={`shrink-0 rounded-full border px-2.5 py-1 text-xs font-semibold ${scoreBadgeClasses(
                      item.score
                    )}`}
                  >
                    {item.score}/100
                  </span>
                </div>

                <div className="flex flex-wrap items-center gap-3 text-xs text-slate-500">
                  <span className="rounded-full bg-slate-100 px-2.5 py-1 font-medium text-slate-600">
                    {item.difficulty}
                  </span>
                  <span className="flex items-center gap-1">
                    <Calendar className="h-3 w-3" />
                    {formatDate(item.createdAt)}
                  </span>
                </div>
              </div>

              <button
                onClick={() => navigate(`/report/${item.id}`)}
                className="mt-5 flex w-full items-center justify-center gap-1.5 rounded-xl border border-slate-200 bg-white px-3 py-2.5 text-sm font-medium text-slate-600 transition-all group-hover:border-[#00bfa6]/40 group-hover:text-[#00966f]"
              >
                View report
                <ChevronRight className="h-3.5 w-3.5 transition-transform group-hover:translate-x-0.5" />
              </button>
            </div>
          ))}
        </div>
      )}
    </BotLayout>
  )
}
