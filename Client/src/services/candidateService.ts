import type {
  DashboardResponse,
  UserProfile,
} from "../app/pages/CandidateDashboard";

const API_URL = import.meta.env.VITE_API_URL ?? "http://localhost:4000";

const BASE_URL = `${API_URL}/api/candidate`;

export async function fetchDashboard(): Promise<DashboardResponse> {
  const res = await fetch(`${BASE_URL}/dashboard`);
  if (!res.ok) throw new Error(`Failed to load dashboard (${res.status})`);
  return res.json();
}

export async function updateProfile(
  payload: Partial<UserProfile>,
): Promise<UserProfile> {
  const res = await fetch(`${BASE_URL}/profile`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });

  if (!res.ok) throw new Error(`Failed to save profile (${res.status})`);
  return res.json();
}

export async function addSkillApi(skill: string) {
  const res = await fetch(`${BASE_URL}/skills`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ skill }),
  });

  if (!res.ok) throw new Error(`Failed to add skill (${res.status})`);
  return res.json();
}

export async function deleteSkillApi(id: string) {
  const res = await fetch(`${BASE_URL}/skills/${id}`, {
    method: "DELETE",
  });

  if (!res.ok) throw new Error(`Failed to delete skill (${res.status})`);
}

export async function addTargetApi(target: string) {
  const res = await fetch(`${BASE_URL}/targets`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ target }),
  });

  if (!res.ok) throw new Error(`Failed to add target (${res.status})`);
  return res.json();
}

export async function deleteTargetApi(id: string) {
  const res = await fetch(`${BASE_URL}/targets/${id}`, {
    method: "DELETE",
  });

  if (!res.ok) throw new Error(`Failed to remove target (${res.status})`);
}