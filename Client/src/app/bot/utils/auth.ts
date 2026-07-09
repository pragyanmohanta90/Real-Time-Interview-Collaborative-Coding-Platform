const USER_KEY = "user";
const TOKEN_KEY = "token";

export type User = {
  name?: string;
  email?: string;
  role?: "candidate" | "interviewer";
  [key: string]: any;
};

/* ---------------- USER ---------------- */

export const saveUser = (user: User) => {
  localStorage.setItem(USER_KEY, JSON.stringify(user));
};

export const getUser = (): User | null => {
  try {
    const raw = localStorage.getItem(USER_KEY);

    if (!raw || raw === "undefined") return null;

    return JSON.parse(raw) as User;
  } catch {
    return null;
  }
};

export const clearUser = () => {
  localStorage.removeItem(USER_KEY);
};

/* ---------------- TOKEN ---------------- */

export const saveToken = (token: string) => {
  localStorage.setItem(TOKEN_KEY, token); // ❌ no JSON stringify
};

export const getToken = (): string | null => {
  return localStorage.getItem(TOKEN_KEY);
};

export const clearToken = () => {
  localStorage.removeItem(TOKEN_KEY);
};

/* ---------------- COMBINED SAFE ACCESS ---------------- */

export const getAuth = () => {
  const token = getToken();
  const user = getUser();

  return {
    token,
    user,
    isLoggedIn: !!token,
  };
};

/* ---------------- LOGIN STATE ---------------- */

export const isLoggedIn = (): boolean => {
  return !!getToken();
};

// const USER_KEY = "user";
// const TOKEN_KEY = "token";

// export type User = {
//   name?: string;
//   email?: string;
//   role?: "candidate" | "interviewer";
//   [key: string]: any;
// };

// export const saveUser = (user: User) => {
//   localStorage.setItem(USER_KEY, JSON.stringify(user));
// };

// export const getUser = (): User | null => {
//   const raw = localStorage.getItem(TOKEN_KEY);
//   return raw ? (JSON.parse(raw) as User) : null;
// };

// export const clearUser = () => {
//   localStorage.removeItem(USER_KEY);
// };

// export const saveToken = (token: string) => {
//   localStorage.setItem(TOKEN_KEY, token);
// };

// export const getToken = (): string | null => {
//   return localStorage.getItem(TOKEN_KEY);
// };

// export const clearToken = () => {
//   localStorage.removeItem(TOKEN_KEY);
// };

// export const isLoggedIn = (): boolean => {
//   return !!getToken();
// };