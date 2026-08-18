import { Navigate, Outlet } from "react-router";

export default function AuthRedirect() {
  const token = localStorage.getItem("token");
  const userString = localStorage.getItem("user");

  // Not logged in → render Auth
  if (!token || !userString) {
    return <Outlet />;
  }

  try {
    const user = JSON.parse(userString);

    if (user.role === "CANDIDATE") {
      return <Navigate to="/candidate" replace />;
    }

    if (user.role === "INTERVIEWER") {
      return <Navigate to="/interviewer" replace />;
    }

    // Unknown role
    localStorage.removeItem("token");
    localStorage.removeItem("user");

    return <Navigate to="/auth" replace />;
  } catch (error) {
    console.error("Invalid user:", error);

    localStorage.removeItem("token");
    localStorage.removeItem("user");

    return <Navigate to="/auth" replace />;
  }
}
