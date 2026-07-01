import API from "./api";
import type {
  DashboardResponse,
  UserProfile,
} from "../app/pages/CandidateDashboard";

export const fetchDashboard = async (): Promise<DashboardResponse> => {
  const { data } = await API.get("/candidate/dashboard");
  return data;
};

export const updateProfile = async (
  payload: Partial<UserProfile>
): Promise<UserProfile> => {
  const { data } = await API.put("/candidate/profile", payload);
  return data;
};

export const addSkillApi = async (skill: string) => {
  const { data } = await API.post("/candidate/skills", {
    skill,
  });

  return data;
};

export const deleteSkillApi = async (id: string) => {
  await API.delete(`/candidate/skills/${id}`);
};

export const addTargetApi = async (target: string) => {
  const { data } = await API.post("/candidate/targets", {
    target,
  });

  return data;
};

export const deleteTargetApi = async (id: string) => {
  await API.delete(`/candidate/targets/${id}`);
};