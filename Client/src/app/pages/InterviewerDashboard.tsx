import { useState } from "react";
import {
  LayoutDashboard, PlusCircle, Video, User,
  Clock, CheckCircle, Users, Calendar, ChevronRight,
  BarChart2, TrendingUp, Star, Briefcase, Filter,
  Copy, Trash2, Edit3, Eye, MoreHorizontal
} from "lucide-react";
import {
  BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip,
  ResponsiveContainer, LineChart, Line, AreaChart, Area,
} from "recharts";
import { DashboardLayout } from "../components/DashboardLayout";

const navItems = [
  { id: "dashboard", label: "Dashboard", icon: <LayoutDashboard className="w-4 h-4" /> },
  { id: "create", label: "Create Interview", icon: <PlusCircle className="w-4 h-4" /> },
  { id: "rooms", label: "Interview Rooms", icon: <Video className="w-4 h-4" /> },
  { id: "profile", label: "Profile", icon: <User className="w-4 h-4" /> },
];

const weeklyData = [
  { day: "Mon", conducted: 2, scheduled: 1 },
  { day: "Tue", conducted: 4, scheduled: 2 },
  { day: "Wed", conducted: 1, scheduled: 3 },
  { day: "Thu", conducted: 5, scheduled: 1 },
  { day: "Fri", conducted: 3, scheduled: 2 },
  { day: "Sat", conducted: 0, scheduled: 0 },
  { day: "Sun", conducted: 1, scheduled: 0 },
];

const offerRateData = [
  { month: "Jan", rate: 22 },
  { month: "Feb", rate: 28 },
  { month: "Mar", rate: 25 },
  { month: "Apr", rate: 31 },
  { month: "May", rate: 38 },
  { month: "Jun", rate: 35 },
];

const rooms = [
  { id: "R-5821", title: "Senior Frontend — React Specialist", candidate: "Priya Nair", role: "Senior Frontend Engineer", date: "Jun 16, 2026 · 2:00 PM", status: "upcoming", duration: "60 min", score: null },
  { id: "R-4490", title: "Backend Engineer — System Design", candidate: "David Kim", role: "Backend Engineer", date: "Jun 14, 2026 · 11:00 AM", status: "completed", duration: "58 min", score: 84 },
  { id: "R-3811", title: "Data Engineer — SQL & Pipelines", candidate: "Sofia Rossi", role: "Data Engineer", date: "Jun 12, 2026 · 3:30 PM", status: "completed", duration: "47 min", score: 71 },
  { id: "R-6102", title: "Staff Engineer — Full Loop", candidate: "James Wright", role: "Staff Engineer", date: "Jun 20, 2026 · 10:00 AM", status: "upcoming", duration: "3 hrs", score: null },
  { id: "R-2903", title: "Product Engineer — Behavioral", candidate: "Ananya Gupta", role: "Product Engineer", date: "Jun 10, 2026 · 1:00 PM", status: "completed", duration: "35 min", score: 90 },
];

const recentCandidates = [
  { name: "David Kim", role: "Backend Engineer", score: 84, decision: "Advance", initials: "DK", color: "bg-blue-500" },
  { name: "Sofia Rossi", role: "Data Engineer", score: 71, decision: "Hold", initials: "SR", color: "bg-violet-500" },
  { name: "Ananya Gupta", role: "Product Engineer", score: 90, decision: "Advance", initials: "AG", color: "bg-emerald-500" },
];

const questionBanks = [
  { name: "React — Senior Level", count: 24, used: 18 },
  { name: "System Design — Scale", count: 16, used: 11 },
  { name: "Behavioral — Leadership", count: 20, used: 14 },
  { name: "Data Structures", count: 32, used: 22 },
];

function SectionHeader({ title, subtitle }: { title: string; subtitle?: string }) {
  return (
    <div className="mb-6">
      <h2 className="text-[#0d1b2a]" style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 700, fontSize: "1.35rem" }}>
        {title}
      </h2>
      {subtitle && <p className="text-[#4a6080] text-sm mt-1">{subtitle}</p>}
    </div>
  );
}

function StatCard({ icon, label, value, sub, dark }: { icon: React.ReactNode; label: string; value: string; sub?: string; dark?: boolean }) {
  return (
    <div className={`rounded-2xl p-5 border ${dark ? "bg-[#0d1b2a] border-[#0d1b2a]" : "bg-white border-[#0d1b2a]/8"}`}>
      <div className={`w-9 h-9 rounded-xl flex items-center justify-center mb-3 ${dark ? "bg-white/10" : "bg-[#0d1b2a]/6"}`}>
        <span className={dark ? "text-[#4d9de0]" : "text-[#4d9de0]"}>{icon}</span>
      </div>
      <div className={`text-2xl mb-0.5 ${dark ? "text-white" : "text-[#0d1b2a]"}`} style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 700 }}>
        {value}
      </div>
      <div className={`text-sm ${dark ? "text-white/60" : "text-[#4a6080]"}`}>{label}</div>
      {sub && <div className={`text-xs mt-1 ${dark ? "text-[#4d9de0]" : "text-[#4d9de0]"}`}>{sub}</div>}
    </div>
  );
}

/* ── Sections ── */

function DashboardSection() {
  return (
    <div>
      <SectionHeader title="Overview" subtitle="This week you have conducted 16 sessions across 4 open roles." />

      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
        <StatCard icon={<Video className="w-4.5 h-4.5" />} label="Interviews Conducted" value="47" sub="↑ 9 this month" />
        <StatCard icon={<Users className="w-4.5 h-4.5" />} label="Candidates Reviewed" value="38" sub="5 pending" dark />
        <StatCard icon={<TrendingUp className="w-4.5 h-4.5" />} label="Avg. Score Given" value="76" sub="Out of 100" />
        <StatCard icon={<CheckCircle className="w-4.5 h-4.5" />} label="Offer Rate" value="35%" sub="↑ 7% vs last month" />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-5 gap-4 mb-4">
        {/* Bar chart */}
        <div className="lg:col-span-3 bg-white rounded-2xl border border-[#0d1b2a]/8 p-6">
          <p className="text-[#0d1b2a] text-sm mb-4" style={{ fontWeight: 600 }}>This Week — Conducted vs Scheduled</p>
          <ResponsiveContainer width="100%" height={200}>
            <BarChart data={weeklyData} barGap={4}>
              <CartesianGrid strokeDasharray="3 3" stroke="#f0f4f8" vertical={false} />
              <XAxis dataKey="day" tick={{ fontSize: 11, fill: "#4a6080" }} axisLine={false} tickLine={false} />
              <YAxis tick={{ fontSize: 11, fill: "#4a6080" }} axisLine={false} tickLine={false} />
              <Tooltip contentStyle={{ borderRadius: 10, border: "1px solid #dde6ef", fontSize: 12 }} />
              <Bar dataKey="conducted" fill="#0d1b2a" radius={[4, 4, 0, 0]} name="Conducted" />
              <Bar dataKey="scheduled" fill="#4d9de0" radius={[4, 4, 0, 0]} name="Scheduled" opacity={0.6} />
            </BarChart>
          </ResponsiveContainer>
        </div>

        {/* Offer rate line */}
        <div className="lg:col-span-2 bg-white rounded-2xl border border-[#0d1b2a]/8 p-6">
          <p className="text-[#0d1b2a] text-sm mb-4" style={{ fontWeight: 600 }}>Offer Rate — 6 Months</p>
          <ResponsiveContainer width="100%" height={200}>
            <AreaChart data={offerRateData}>
              <defs>
                <linearGradient id="rateGrad" x1="0" y1="0" x2="0" y2="1">
                  <stop offset="5%" stopColor="#4d9de0" stopOpacity={0.15} />
                  <stop offset="95%" stopColor="#4d9de0" stopOpacity={0} />
                </linearGradient>
              </defs>
              <CartesianGrid strokeDasharray="3 3" stroke="#f0f4f8" />
              <XAxis dataKey="month" tick={{ fontSize: 11, fill: "#4a6080" }} axisLine={false} tickLine={false} />
              <YAxis tick={{ fontSize: 11, fill: "#4a6080" }} axisLine={false} tickLine={false} unit="%" />
              <Tooltip contentStyle={{ borderRadius: 10, border: "1px solid #dde6ef", fontSize: 12 }} />
              <Area type="monotone" dataKey="rate" stroke="#4d9de0" strokeWidth={2.5} fill="url(#rateGrad)" dot={{ r: 3, fill: "#4d9de0" }} />
            </AreaChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Recent candidates */}
      <div className="bg-white rounded-2xl border border-[#0d1b2a]/8 p-6">
        <div className="flex items-center justify-between mb-4">
          <p className="text-[#0d1b2a] text-sm" style={{ fontWeight: 600 }}>Recent Candidate Reviews</p>
          <button className="text-[#4d9de0] text-xs hover:underline">View all</button>
        </div>
        <div className="space-y-3">
          {recentCandidates.map((c) => (
            <div key={c.name} className="flex items-center gap-4 p-3.5 rounded-xl bg-[#f0f4f8]">
              <div className={`w-9 h-9 rounded-full ${c.color} flex items-center justify-center text-xs text-white`} style={{ fontWeight: 700 }}>
                {c.initials}
              </div>
              <div className="flex-1 min-w-0">
                <p className="text-[#0d1b2a] text-sm" style={{ fontWeight: 500 }}>{c.name}</p>
                <p className="text-[#4a6080] text-xs">{c.role}</p>
              </div>
              <div className="text-right">
                <p className="text-[#0d1b2a] text-sm" style={{ fontWeight: 700 }}>{c.score}/100</p>
              </div>
              <span className={`text-xs px-2.5 py-1 rounded-full border ${c.decision === "Advance" ? "bg-emerald-50 text-emerald-700 border-emerald-200" : "bg-amber-50 text-amber-700 border-amber-200"}`} style={{ fontWeight: 500 }}>
                {c.decision}
              </span>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

function CreateSection() {
  const [step, setStep] = useState(1);
  const [form, setForm] = useState({
    title: "",
    role: "",
    type: "",
    duration: "60",
    date: "",
    time: "",
    notes: "",
  });

  const types = [
    { id: "technical", label: "Technical", icon: "💻" },
    { id: "behavioral", label: "Behavioral", icon: "🎤" },
    { id: "system", label: "System Design", icon: "🏗️" },
    { id: "full", label: "Full Loop", icon: "🔄" },
  ];

  const inputCls = "w-full bg-white border border-[#0d1b2a]/12 rounded-xl px-4 py-3 text-[#0d1b2a] placeholder-[#4a6080]/50 text-sm focus:outline-none focus:ring-2 focus:ring-[#4d9de0]/30 focus:border-[#4d9de0]/60 transition-all";

  return (
    <div>
      <SectionHeader title="Create Interview" subtitle="Set up a new interview room in under 2 minutes." />

      {/* Step indicator */}
      <div className="flex items-center gap-0 mb-8">
        {[1, 2, 3].map((s, i) => (
          <div key={s} className="flex items-center">
            <button
              onClick={() => setStep(s)}
              className={`w-8 h-8 rounded-full flex items-center justify-center text-xs transition-all ${step >= s ? "bg-[#0d1b2a] text-white" : "bg-white border border-[#0d1b2a]/15 text-[#4a6080]"}`}
              style={{ fontWeight: 600 }}
            >
              {s}
            </button>
            <span className={`ml-2 text-xs mr-6 ${step >= s ? "text-[#0d1b2a]" : "text-[#4a6080]"}`} style={{ fontWeight: step === s ? 600 : 400 }}>
              {["Role & Type", "Schedule", "Question Kit"][s - 1]}
            </span>
            {i < 2 && <div className={`w-8 h-px mr-6 ${step > s ? "bg-[#0d1b2a]" : "bg-[#0d1b2a]/15"}`} />}
          </div>
        ))}
      </div>

      <div className="bg-white rounded-2xl border border-[#0d1b2a]/8 p-7 max-w-2xl">
        {step === 1 && (
          <div className="space-y-5">
            <div>
              <label className="block text-[#0d1b2a] text-sm mb-1.5" style={{ fontWeight: 500 }}>Interview Title</label>
              <input className={inputCls} placeholder="e.g. Senior Frontend — React Specialist" value={form.title} onChange={(e) => setForm({ ...form, title: e.target.value })} />
            </div>
            <div>
              <label className="block text-[#0d1b2a] text-sm mb-1.5" style={{ fontWeight: 500 }}>Target Role</label>
              <input className={inputCls} placeholder="e.g. Senior Software Engineer" value={form.role} onChange={(e) => setForm({ ...form, role: e.target.value })} />
            </div>
            <div>
              <label className="block text-[#0d1b2a] text-sm mb-2" style={{ fontWeight: 500 }}>Interview Type</label>
              <div className="grid grid-cols-2 gap-3">
                {types.map((t) => (
                  <button
                    key={t.id}
                    onClick={() => setForm({ ...form, type: t.id })}
                    className={`flex items-center gap-3 p-4 rounded-xl border text-left transition-all ${form.type === t.id ? "bg-[#0d1b2a] border-[#0d1b2a] text-white" : "bg-[#f0f4f8] border-[#0d1b2a]/8 text-[#4a6080] hover:border-[#0d1b2a]/20"}`}
                  >
                    <span className="text-lg">{t.icon}</span>
                    <span className="text-sm" style={{ fontWeight: form.type === t.id ? 600 : 400 }}>{t.label}</span>
                  </button>
                ))}
              </div>
            </div>
          </div>
        )}

        {step === 2 && (
          <div className="space-y-5">
            <div className="grid grid-cols-2 gap-4">
              <div>
                <label className="block text-[#0d1b2a] text-sm mb-1.5" style={{ fontWeight: 500 }}>Date</label>
                <input type="date" className={inputCls} value={form.date} onChange={(e) => setForm({ ...form, date: e.target.value })} />
              </div>
              <div>
                <label className="block text-[#0d1b2a] text-sm mb-1.5" style={{ fontWeight: 500 }}>Time</label>
                <input type="time" className={inputCls} value={form.time} onChange={(e) => setForm({ ...form, time: e.target.value })} />
              </div>
            </div>
            <div>
              <label className="block text-[#0d1b2a] text-sm mb-1.5" style={{ fontWeight: 500 }}>Duration</label>
              <select className={inputCls} value={form.duration} onChange={(e) => setForm({ ...form, duration: e.target.value })}>
                {["30", "45", "60", "90", "120"].map((d) => (
                  <option key={d} value={d}>{d} minutes</option>
                ))}
              </select>
            </div>
            <div>
              <label className="block text-[#0d1b2a] text-sm mb-1.5" style={{ fontWeight: 500 }}>Candidate Email</label>
              <input type="email" className={inputCls} placeholder="candidate@email.com" />
            </div>
            <div>
              <label className="block text-[#0d1b2a] text-sm mb-1.5" style={{ fontWeight: 500 }}>Notes for Candidate <span className="text-[#4a6080] font-normal">(optional)</span></label>
              <textarea className={`${inputCls} resize-none h-24`} placeholder="Preparation tips, what to expect, dress code..." value={form.notes} onChange={(e) => setForm({ ...form, notes: e.target.value })} />
            </div>
          </div>
        )}

        {step === 3 && (
          <div className="space-y-4">
            <p className="text-[#4a6080] text-sm">Select question banks to include in this session.</p>
            {questionBanks.map((q) => (
              <label key={q.name} className="flex items-center gap-4 p-4 rounded-xl border border-[#0d1b2a]/8 bg-[#f0f4f8] cursor-pointer hover:border-[#0d1b2a]/20 transition-colors">
                <input type="checkbox" className="w-4 h-4 accent-[#0d1b2a]" defaultChecked />
                <div className="flex-1">
                  <p className="text-[#0d1b2a] text-sm" style={{ fontWeight: 500 }}>{q.name}</p>
                  <p className="text-[#4a6080] text-xs">{q.count} questions · {q.used} used previously</p>
                </div>
              </label>
            ))}
            <div className="mt-6 p-4 bg-[#0d1b2a]/4 rounded-xl border border-[#0d1b2a]/8">
              <p className="text-[#0d1b2a] text-sm mb-1" style={{ fontWeight: 600 }}>Ready to create?</p>
              <p className="text-[#4a6080] text-xs">A room link will be generated and emailed to the candidate automatically.</p>
            </div>
          </div>
        )}

        <div className="flex items-center justify-between mt-7 pt-5 border-t border-[#0d1b2a]/8">
          <button
            onClick={() => setStep(Math.max(1, step - 1))}
            disabled={step === 1}
            className="text-[#4a6080] text-sm disabled:opacity-30 hover:text-[#0d1b2a] transition-colors"
            style={{ fontWeight: 500 }}
          >
            ← Back
          </button>
          <button
            onClick={() => {
              if (step < 3) setStep(step + 1);
            }}
            className="bg-[#0d1b2a] text-white text-sm px-6 py-2.5 rounded-xl hover:bg-[#1a2f45] transition-colors"
            style={{ fontWeight: 600 }}
          >
            {step === 3 ? "Create Room →" : "Continue →"}
          </button>
        </div>
      </div>
    </div>
  );
}

function RoomsSection() {
  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <div>
          <h2 className="text-[#0d1b2a]" style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 700, fontSize: "1.35rem" }}>Interview Rooms</h2>
          <p className="text-[#4a6080] text-sm mt-1">Manage all your created interview sessions.</p>
        </div>
        <button className="flex items-center gap-2 bg-[#0d1b2a] text-white text-sm px-4 py-2.5 rounded-xl hover:bg-[#1a2f45] transition-colors" style={{ fontWeight: 600 }}>
          <PlusCircle className="w-3.5 h-3.5" /> New Room
        </button>
      </div>

      <div className="space-y-3">
        {rooms.map((r) => (
          <div key={r.id} className="bg-white rounded-2xl border border-[#0d1b2a]/8 p-5 hover:border-[#4d9de0]/30 transition-all">
            <div className="flex items-start gap-4">
              <div className="w-11 h-11 rounded-xl bg-[#0d1b2a]/6 flex items-center justify-center shrink-0">
                <Video className="w-5 h-5 text-[#0d1b2a]" />
              </div>
              <div className="flex-1 min-w-0">
                <div className="flex items-start justify-between gap-3 flex-wrap">
                  <div>
                    <h3 className="text-[#0d1b2a] mb-0.5" style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 600, fontSize: "0.95rem" }}>{r.title}</h3>
                    <p className="text-[#4a6080] text-xs">{r.candidate} · {r.role}</p>
                  </div>
                  <span className={`text-xs px-2.5 py-1 rounded-full border shrink-0 ${r.status === "upcoming" ? "bg-amber-50 text-amber-700 border-amber-200" : "bg-emerald-50 text-emerald-700 border-emerald-200"}`} style={{ fontWeight: 500 }}>
                    {r.status === "upcoming" ? "Upcoming" : "Completed"}
                  </span>
                </div>
                <div className="flex items-center gap-5 mt-3 flex-wrap">
                  <span className="text-[#4a6080] text-xs flex items-center gap-1.5"><Calendar className="w-3 h-3" />{r.date}</span>
                  <span className="text-[#4a6080] text-xs flex items-center gap-1.5"><Clock className="w-3 h-3" />{r.duration}</span>
                  <span className="text-[#4a6080] text-xs font-mono bg-[#f0f4f8] px-2 py-0.5 rounded">{r.id}</span>
                  {r.score !== null && (
                    <span className="text-[#4d9de0] text-xs" style={{ fontWeight: 600 }}>Score: {r.score}/100</span>
                  )}
                </div>
              </div>
              <div className="flex items-center gap-2 shrink-0">
                {r.status === "upcoming" ? (
                  <>
                    <button className="w-8 h-8 rounded-lg bg-[#f0f4f8] flex items-center justify-center text-[#4a6080] hover:text-[#0d1b2a] hover:bg-[#dde6ef] transition-colors" title="Edit">
                      <Edit3 className="w-3.5 h-3.5" />
                    </button>
                    <button className="w-8 h-8 rounded-lg bg-[#f0f4f8] flex items-center justify-center text-[#4a6080] hover:text-[#0d1b2a] hover:bg-[#dde6ef] transition-colors" title="Copy link">
                      <Copy className="w-3.5 h-3.5" />
                    </button>
                    <button className="bg-[#0d1b2a] text-white text-xs px-4 py-2 rounded-lg hover:bg-[#1a2f45] transition-colors" style={{ fontWeight: 600 }}>
                      Start
                    </button>
                  </>
                ) : (
                  <>
                    <button className="w-8 h-8 rounded-lg bg-[#f0f4f8] flex items-center justify-center text-[#4a6080] hover:text-[#0d1b2a] hover:bg-[#dde6ef] transition-colors" title="View scorecard">
                      <Eye className="w-3.5 h-3.5" />
                    </button>
                    <button className="w-8 h-8 rounded-lg bg-[#f0f4f8] flex items-center justify-center text-rose-400 hover:bg-rose-50 transition-colors" title="Delete">
                      <Trash2 className="w-3.5 h-3.5" />
                    </button>
                  </>
                )}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

function ProfileSection() {
  const specializations = ["Frontend", "System Design", "Behavioral", "Full-Stack", "Data Engineering"];
  return (
    <div>
      <SectionHeader title="Profile" subtitle="Your interviewer profile seen by candidates and the platform." />
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
        {/* Profile card */}
        <div className="bg-white rounded-2xl border border-[#0d1b2a]/8 p-7 flex flex-col items-center text-center">
          <div className="w-20 h-20 rounded-full bg-[#1a4a7a] flex items-center justify-center text-white text-2xl mb-4" style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 700 }}>
            SL
          </div>
          <h3 className="text-[#0d1b2a] mb-0.5" style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 700, fontSize: "1.1rem" }}>Sarah Lin</h3>
          <p className="text-[#4a6080] text-sm mb-1">sarah.lin@company.com</p>
          <p className="text-[#4a6080] text-xs mb-4">Engineering Manager · San Francisco, CA</p>
          <div className="flex gap-1.5 flex-wrap justify-center mb-5">
            {specializations.map((s) => (
              <span key={s} className="text-xs bg-[#f0f4f8] text-[#4a6080] border border-[#0d1b2a]/8 px-2.5 py-1 rounded-full">{s}</span>
            ))}
          </div>
          <div className="w-full border-t border-[#0d1b2a]/8 pt-4">
            <div className="grid grid-cols-3 gap-2 text-center">
              {[{ v: "47", l: "Interviews" }, { v: "35%", l: "Offer Rate" }, { v: "4.8★", l: "Rating" }].map(({ v, l }) => (
                <div key={l}>
                  <p className="text-[#0d1b2a] text-lg" style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 700 }}>{v}</p>
                  <p className="text-[#4a6080] text-xs">{l}</p>
                </div>
              ))}
            </div>
          </div>
          <button className="mt-5 w-full border border-[#0d1b2a]/15 text-[#0d1b2a] text-sm py-2.5 rounded-xl hover:bg-[#f0f4f8] transition-colors" style={{ fontWeight: 500 }}>
            Edit Profile
          </button>
        </div>

        {/* Details */}
        <div className="lg:col-span-2 space-y-4">
          <div className="bg-white rounded-2xl border border-[#0d1b2a]/8 p-6">
            <p className="text-[#0d1b2a] text-sm mb-3" style={{ fontWeight: 600 }}>Bio</p>
            <p className="text-[#4a6080] text-sm leading-relaxed">
              Engineering Manager at Stripe with 8 years in full-stack development and 3 years in technical hiring. Conducted 200+ interviews across frontend, backend, and leadership roles. Focused on calibrated, fair assessments.
            </p>
          </div>

          <div className="bg-white rounded-2xl border border-[#0d1b2a]/8 p-6">
            <p className="text-[#0d1b2a] text-sm mb-4" style={{ fontWeight: 600 }}>Interview Specializations</p>
            <div className="grid grid-cols-2 gap-3">
              {[
                { area: "Technical Rounds", count: "24 sessions" },
                { area: "System Design", count: "11 sessions" },
                { area: "Behavioral", count: "8 sessions" },
                { area: "Full Loop", count: "4 sessions" },
              ].map(({ area, count }) => (
                <div key={area} className="bg-[#f0f4f8] rounded-xl p-4 border border-[#0d1b2a]/6">
                  <p className="text-[#0d1b2a] text-sm" style={{ fontWeight: 500 }}>{area}</p>
                  <p className="text-[#4a6080] text-xs mt-0.5">{count}</p>
                </div>
              ))}
            </div>
          </div>

          <div className="bg-white rounded-2xl border border-[#0d1b2a]/8 p-6">
            <div className="flex items-center justify-between mb-4">
              <p className="text-[#0d1b2a] text-sm" style={{ fontWeight: 600 }}>Question Banks Owned</p>
              <button className="text-xs text-[#4d9de0] hover:underline">Manage</button>
            </div>
            <div className="space-y-2.5">
              {questionBanks.map((q) => (
                <div key={q.name} className="flex items-center justify-between">
                  <p className="text-[#4a6080] text-sm">{q.name}</p>
                  <span className="text-xs text-[#4a6080] bg-[#f0f4f8] border border-[#0d1b2a]/8 px-2 py-0.5 rounded">{q.count} Qs</span>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default function InterviewerDashboard() {
  const [activeSection, setActiveSection] = useState("dashboard");

  const sections: Record<string, React.ReactNode> = {
    dashboard: <DashboardSection />,
    create: <CreateSection />,
    rooms: <RoomsSection />,
    profile: <ProfileSection />,
  };

  return (
    <DashboardLayout
      role="interviewer"
      navItems={navItems}
      activeSection={activeSection}
      onSectionChange={setActiveSection}
      userName="Sarah Lin"
      userInitials="SL"
    >
      <div className="flex items-center justify-between mb-8">
        <div>
          <h1 className="text-[#0d1b2a] leading-tight" style={{ fontFamily: "'Roboto Slab', serif", fontWeight: 700, fontSize: "1.55rem" }}>
            Welcome back, Sarah 👋
          </h1>
          <p className="text-[#4a6080] text-sm mt-0.5">You have 2 interviews scheduled this week.</p>
        </div>
        <button
          onClick={() => setActiveSection("create")}
          className="hidden sm:flex items-center gap-2 bg-[#0d1b2a] text-white px-5 py-2.5 rounded-xl hover:bg-[#1a2f45] transition-colors"
          style={{ fontWeight: 600, fontSize: "0.875rem" }}
        >
          <PlusCircle className="w-3.5 h-3.5" /> Create Interview
        </button>
      </div>

      {sections[activeSection]}
    </DashboardLayout>
  );
}
