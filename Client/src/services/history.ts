import API from "./api";

export const getHistory = async (): Promise<any> => {
  const { data } = await API.get("/history");

  return data;
};

export const getInterviewById = async (id: string): Promise<any> => {
  const { data } = await API.get(`/history/${id}`);

  return data;
};
