import { Pause, Play, Square, Volume2 } from "lucide-react";
import { useTextToSpeech } from "../hooks/useTextToSpeech";

interface SpeakerButtonProps {
  text: string;
  className?: string;
  size?: "sm" | "md";
}

/**
 * Small speaker control that reads `text` aloud using speechSynthesis.
 * Shows play / pause / stop depending on state. Self-contained — each
 * instance owns its own utterance, so multiple can exist on one page.
 */
export default function SpeakerButton({
  text,
  className = "",
  size = "sm",
}: SpeakerButtonProps) {
  const { isSupported, status, activeText, speak, pause, resume, stop } =
    useTextToSpeech();

  if (!isSupported) return null;

  const isThisActive = activeText === text;
  const dim = size === "sm" ? "h-8 w-8" : "h-10 w-10";
  const icon = size === "sm" ? 14 : 16;

  const handlePrimaryClick = () => {
    if (!isThisActive || status === "idle") {
      speak(text);
    } else if (status === "speaking") {
      pause();
    } else if (status === "paused") {
      resume();
    }
  };

  return (
    <div className={`inline-flex items-center gap-1 ${className}`}>
      <button
        type="button"
        onClick={handlePrimaryClick}
        title={
          isThisActive && status === "speaking"
            ? "Pause"
            : isThisActive && status === "paused"
            ? "Resume"
            : "Read aloud"
        }
        className={`${dim} flex items-center justify-center rounded-full border border-slate-200 bg-white text-slate-500 shadow-sm transition-all hover:border-[#00bfa6]/40 hover:text-[#00bfa6] ${
          isThisActive && status === "speaking"
            ? "border-[#00bfa6]/40 text-[#00bfa6]"
            : ""
        }`}
      >
        {isThisActive && status === "speaking" ? (
          <Pause size={icon} />
        ) : isThisActive && status === "paused" ? (
          <Play size={icon} />
        ) : (
          <Volume2 size={icon} />
        )}
      </button>

      {isThisActive && status !== "idle" && (
        <button
          type="button"
          onClick={stop}
          title="Stop"
          className={`${dim} flex items-center justify-center rounded-full border border-slate-200 bg-white text-slate-400 shadow-sm transition-all hover:border-red-300 hover:text-red-500`}
        >
          <Square size={icon - 3} />
        </button>
      )}
    </div>
  );
}
