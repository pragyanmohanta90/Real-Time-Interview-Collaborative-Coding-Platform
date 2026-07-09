import { AlertTriangle, RotateCw } from "lucide-react"

interface ErrorBannerProps {
  message?: string
  onRetry?: () => void
}

export default function ErrorBanner({ message, onRetry }: ErrorBannerProps) {
  if (!message) return null

  return (
    <div className="flex items-center justify-between gap-4 rounded-2xl border border-red-100 bg-red-50/80 px-4 py-3 text-sm text-red-700 backdrop-blur-sm">
      <div className="flex items-center gap-2.5">
        <AlertTriangle className="h-4 w-4 shrink-0 text-red-500" />
        <span>{message}</span>
      </div>

      {onRetry && (
        <button
          onClick={onRetry}
          className="flex shrink-0 items-center gap-1.5 rounded-lg bg-red-600 px-3 py-1.5 text-xs font-semibold text-white transition-colors hover:bg-red-700"
        >
          <RotateCw className="h-3 w-3" />
          Retry
        </button>
      )}
    </div>
  )
}
