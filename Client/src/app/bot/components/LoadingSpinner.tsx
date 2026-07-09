import { Sparkles } from "lucide-react"

interface LoadingSpinnerProps {
  label?: string
}

export default function LoadingSpinner({ label = 'Loading...' }: LoadingSpinnerProps) {
  return (
    <div className="flex flex-col items-center justify-center gap-4 py-24">
      <div className="relative flex h-14 w-14 items-center justify-center">
        <div className="absolute inset-0 animate-spin rounded-full border-[3px] border-[#00bfa6]/15 border-t-[#00bfa6]" />
        <Sparkles className="h-5 w-5 text-[#00bfa6]" />
      </div>
      <p className="text-sm font-medium text-slate-500">{label}</p>
    </div>
  )
}
