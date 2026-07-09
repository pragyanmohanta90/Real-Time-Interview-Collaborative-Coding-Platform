import { useEffect, useRef, useState } from 'react'
import { useLocation, useNavigate } from 'react-router'
import { Mic, MicOff, Send, Sparkles, User } from 'lucide-react'
import { evaluateInterview } from '../../../services/interview'
import ErrorBanner from '../components/ErrorBanner'
import LoadingSpinner from '../components/LoadingSpinner'
import SpeakerButton from '../components/SpeakerButton'
import BotLayout from '../components/BotLayout'
import { useSpeechToText } from '../hooks/useSpeechToText'

interface InterviewState {
  questions: string[]
  role: string
  experience: string
  difficulty: string
}

interface Answers {
  [key: string]: string
}

interface LocationState {
  state: InterviewState
}

export default function Interview() {
  const navigate = useNavigate()
  const location = useLocation() as unknown as LocationState

  const state = location.state

  const [currentIndex, setCurrentIndex] = useState<number>(0)
  const [answers, setAnswers] = useState<Answers>({})
  const [loading, setLoading] = useState<boolean>(false)
  const [error, setError] = useState<string>('')
  const [entering, setEntering] = useState(true)
  const textareaRef = useRef<HTMLTextAreaElement>(null)

  // Guard against direct navigation
  const hasSession = !!(state && state.questions && state.questions.length > 0)

  const questions = hasSession ? state.questions : []
  const role = hasSession ? state.role : ''
  const experience = hasSession ? state.experience : ''
  const difficulty = hasSession ? state.difficulty : ''

  const total = questions.length
  const currentQuestion = questions[currentIndex] ?? ''
  const currentKey = String(currentIndex + 1)

  // ---------- Voice input (speech-to-text) ----------
  const {
    isSupported: sttSupported,
    isListening,
    interimText,
    error: sttError,
    toggle: toggleListening,
    stop: stopListening,
  } = useSpeechToText({
    onTranscriptChange: (chunk, isFinal) => {
      if (!isFinal) return
      setAnswers((prev) => {
        const existing = prev[currentKey] || ''
        const needsSpace = existing && !existing.endsWith(' ')
        return {
          ...prev,
          [currentKey]: `${existing}${needsSpace ? ' ' : ''}${chunk}`,
        }
      })
    },
  })

  // Stop listening whenever the question changes, so voice input never
  // bleeds across questions.
  useEffect(() => {
    stopListening()
    setEntering(true)
    const t = setTimeout(() => setEntering(false), 20)
    return () => clearTimeout(t)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [currentIndex])

  const handleAnswerChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setAnswers({
      ...answers,
      [currentKey]: e.target.value,
    })
  }

  const goNext = () => {
    if (currentIndex < total - 1) setCurrentIndex(currentIndex + 1)
  }

  const goPrevious = () => {
    if (currentIndex > 0) setCurrentIndex(currentIndex - 1)
  }

  const handleFinish = async () => {
    setError('')
    setLoading(true)
    stopListening()

    try {
      const result = await evaluateInterview({
        role,
        experience,
        difficulty,
        questions,
        answers,
      })

      navigate('/report', {
        state: {
          evaluation: (result as any).evaluation,
          interviewId: (result as any).interviewId,
          role,
        },
      })
    } catch (err: any) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  if (!hasSession) {
    return (
      <BotLayout title="AI Mock Interview">
        <div className="mx-auto mt-16 max-w-md text-center">
          <p className="mb-4 text-slate-600">No active interview found.</p>
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

  if (loading) {
    return (
      <BotLayout title="AI Mock Interview" subtitle={`${role} · ${difficulty}`}>
        <LoadingSpinner label="Evaluating your interview..." />
      </BotLayout>
    )
  }

  const progressPct = ((currentIndex + 1) / total) * 100
  const answerValue = answers[currentKey] || ''

  return (
    <BotLayout title={role || 'Mock Interview'} subtitle={`${difficulty} · Question ${currentIndex + 1} of ${total}`}>
      {/* Thin top progress bar */}
      <div className="mb-8 h-1 w-full overflow-hidden rounded-full bg-slate-200/70">
        <div
          className="h-full rounded-full bg-gradient-to-r from-[#00bfa6] to-[#4d9de0] transition-all duration-500 ease-out"
          style={{ width: `${progressPct}%` }}
        />
      </div>

      {error && (
        <div className="mb-5">
          <ErrorBanner message={error} onRetry={handleFinish} />
        </div>
      )}

      <div className="mx-auto flex max-w-2xl flex-col gap-5">
        {/* AI question bubble */}
        <div
          className={`flex items-start gap-3 transition-all duration-300 ${
            entering ? 'translate-y-2 opacity-0' : 'translate-y-0 opacity-100'
          }`}
        >
          <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full bg-[#0d1b2a] shadow-sm">
            <Sparkles className="h-4 w-4 text-[#00bfa6]" />
          </div>

          <div className="min-w-0 flex-1">
            <div className="rounded-2xl rounded-tl-sm border border-white/60 bg-white/80 px-4 py-3.5 shadow-[0_4px_20px_-8px_rgba(13,27,42,0.15)] backdrop-blur-xl">
              <div className="flex items-start justify-between gap-3">
                <p className="text-[15px] leading-relaxed text-slate-800">
                  {currentQuestion}
                </p>
                <SpeakerButton text={currentQuestion} className="shrink-0" />
              </div>
            </div>
          </div>
        </div>

        {/* Answer chat box */}
        <div
          className={`flex items-start gap-3 transition-all duration-300 delay-75 ${
            entering ? 'translate-y-2 opacity-0' : 'translate-y-0 opacity-100'
          }`}
        >
          <div className="flex h-9 w-9 shrink-0 items-center justify-center rounded-full bg-slate-200">
            <User className="h-4 w-4 text-slate-500" />
          </div>

          <div className="min-w-0 flex-1">
            <div
              className={`rounded-2xl rounded-tl-sm border bg-white/90 shadow-[0_4px_20px_-8px_rgba(13,27,42,0.1)] backdrop-blur-xl transition-colors ${
                isListening ? 'border-red-300' : 'border-white/60'
              }`}
            >
              <textarea
                ref={textareaRef}
                rows={7}
                value={answerValue + (isListening && interimText ? (answerValue ? ' ' : '') + interimText : '')}
                onChange={handleAnswerChange}
                placeholder="Type your answer, or tap the mic to speak..."
                className="w-full resize-none rounded-2xl bg-transparent px-4 py-3.5 text-sm text-slate-800 placeholder:text-slate-400 outline-none"
              />

              <div className="flex items-center justify-between gap-3 border-t border-slate-100 px-4 py-2.5">
                <div className="flex items-center gap-2 text-xs font-medium">
                  {sttSupported ? (
                    <span
                      className={`flex items-center gap-1.5 ${
                        isListening ? 'text-red-500' : 'text-slate-400'
                      }`}
                    >
                      <span
                        className={`h-1.5 w-1.5 rounded-full ${
                          isListening ? 'animate-pulse bg-red-500' : 'bg-slate-300'
                        }`}
                      />
                      {isListening ? 'Listening…' : 'Voice ready'}
                    </span>
                  ) : (
                    <span className="text-slate-400">Voice input not supported</span>
                  )}
                </div>

                {sttSupported && (
                  <button
                    type="button"
                    onClick={toggleListening}
                    title={isListening ? 'Stop voice input' : 'Start voice input'}
                    className={`relative flex h-9 w-9 shrink-0 items-center justify-center rounded-full transition-all ${
                      isListening
                        ? 'bg-red-500 text-white shadow-lg shadow-red-500/30'
                        : 'bg-slate-100 text-slate-500 hover:bg-[#00bfa6]/10 hover:text-[#00bfa6]'
                    }`}
                  >
                    {isListening && (
                      <span className="absolute inset-0 animate-ping rounded-full bg-red-400/60" />
                    )}
                    {isListening ? (
                      <MicOff className="relative h-4 w-4" />
                    ) : (
                      <Mic className="relative h-4 w-4" />
                    )}
                  </button>
                )}
              </div>
            </div>

            {sttError && (
              <p className="mt-1.5 px-1 text-xs text-red-500">{sttError}</p>
            )}
          </div>
        </div>

        {/* Controls */}
        <div className="mt-2 flex items-center justify-between">
          <button
            type="button"
            onClick={goPrevious}
            disabled={currentIndex === 0}
            className="rounded-xl border border-slate-200 bg-white px-4 py-2.5 text-sm font-medium text-slate-600 shadow-sm transition-all hover:bg-slate-50 disabled:cursor-not-allowed disabled:opacity-40"
          >
            Previous
          </button>

          {currentIndex < total - 1 ? (
            <button
              type="button"
              onClick={goNext}
              className="flex items-center gap-2 rounded-xl bg-[#0d1b2a] px-5 py-2.5 text-sm font-semibold text-white shadow-lg shadow-[#0d1b2a]/15 transition-all hover:bg-[#132234] active:scale-[0.99]"
            >
              Next
              <Send className="h-3.5 w-3.5" />
            </button>
          ) : (
            <button
              type="button"
              onClick={handleFinish}
              className="flex items-center gap-2 rounded-xl bg-[#00bfa6] px-5 py-2.5 text-sm font-semibold text-[#062019] shadow-lg shadow-[#00bfa6]/25 transition-all hover:bg-[#00d4b8] active:scale-[0.99]"
            >
              Finish Interview
              <Send className="h-3.5 w-3.5" />
            </button>
          )}
        </div>
      </div>
    </BotLayout>
  )
}
