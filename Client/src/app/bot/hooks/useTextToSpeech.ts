import { useCallback, useEffect, useRef, useState } from "react";

type SpeechStatus = "idle" | "speaking" | "paused";

interface UseTextToSpeechResult {
  isSupported: boolean;
  status: SpeechStatus;
  /** The text currently loaded (so UI can tell which block is being read). */
  activeText: string | null;
  speak: (text: string) => void;
  pause: () => void;
  resume: () => void;
  stop: () => void;
}

/**
 * Wraps window.speechSynthesis to read question / feedback text aloud,
 * with play / pause / stop controls.
 */
export function useTextToSpeech(): UseTextToSpeechResult {
  const isSupported =
    typeof window !== "undefined" && "speechSynthesis" in window;

  const [status, setStatus] = useState<SpeechStatus>("idle");
  const [activeText, setActiveText] = useState<string | null>(null);
  const utteranceRef = useRef<SpeechSynthesisUtterance | null>(null);

  useEffect(() => {
    return () => {
      if (isSupported) window.speechSynthesis.cancel();
    };
  }, [isSupported]);

  const stop = useCallback(() => {
    if (!isSupported) return;
    window.speechSynthesis.cancel();
    utteranceRef.current = null;
    setStatus("idle");
    setActiveText(null);
  }, [isSupported]);

  const speak = useCallback(
    (text: string) => {
      if (!isSupported || !text.trim()) return;

      // Reading something new stops whatever was playing before.
      window.speechSynthesis.cancel();

      const utterance = new SpeechSynthesisUtterance(text);
      utterance.rate = 1;
      utterance.pitch = 1;

      utterance.onstart = () => setStatus("speaking");
      utterance.onresume = () => setStatus("speaking");
      utterance.onpause = () => setStatus("paused");
      utterance.onend = () => {
        setStatus("idle");
        setActiveText(null);
      };
      utterance.onerror = () => {
        setStatus("idle");
        setActiveText(null);
      };

      utteranceRef.current = utterance;
      setActiveText(text);
      window.speechSynthesis.speak(utterance);
    },
    [isSupported]
  );

  const pause = useCallback(() => {
    if (!isSupported) return;
    window.speechSynthesis.pause();
    setStatus("paused");
  }, [isSupported]);

  const resume = useCallback(() => {
    if (!isSupported) return;
    window.speechSynthesis.resume();
    setStatus("speaking");
  }, [isSupported]);

  return { isSupported, status, activeText, speak, pause, resume, stop };
}
