import { Navigate, Outlet } from "react-router";

type Role = "CANDIDATE" | "INTERVIEWER";

interface ProtectedRouteProps {
  allowedRole: Role;
}

export default function ProtectedRoute({
  allowedRole,
}: ProtectedRouteProps) {
  const token = localStorage.getItem("token");
  const userString = localStorage.getItem("user");

  if (!token || !userString) {
    return (
      <Navigate
        to={`/auth?role=${
          allowedRole === "INTERVIEWER"
            ? "interviewer"
            : "candidate"
        }`}
        replace
      />
    );
  }

  let user;

  try {
    user = JSON.parse(userString);
  } catch (error) {
    console.error("Invalid user data:", error);

    localStorage.removeItem("token");
    localStorage.removeItem("user");

    return <Navigate to="/auth" replace />;
  }

  if (user.role !== allowedRole) {
    if (user.role === "CANDIDATE") {
      return <Navigate to="/candidate" replace />;
    }

    if (user.role === "INTERVIEWER") {
      return <Navigate to="/interviewer" replace />;
    }

    localStorage.removeItem("token");
    localStorage.removeItem("user");

    return <Navigate to="/auth" replace />;
  }

  return <Outlet />;
}
