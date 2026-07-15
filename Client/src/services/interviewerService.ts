import API from "./api";

export const getInterviewerDashboard = async () => {
  const response = await API.get("/interviewer/dashboard");
  return response.data;
};

export const updateInterviewerProfile = async (data: any) => {
  const response = await API.put("/interviewer/profile", data);
  return response.data;
};

export const getInterviewRooms = async () => {
  const response = await API.get("/interviewer/rooms");
  return response.data;
};

export const createInterviewRoom = async (data: any) => {
  const response = await API.post("/interviewer/rooms", data);
  return response.data;
};