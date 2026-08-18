import { createBrowserRouter } from "react-router";

import Home from "./pages/Home";
import Auth from "./pages/Auth";
import CandidateDashboard from "./pages/CandidateDashboard";
import InterviewerDashboard from "./pages/InterviewerDashboard";
import CodeEditor from "./pages/CodeEditor";
import ForgotPassword from "./pages/ForgotPassword";
import ResetPassword from "./pages/ResetPassword";
import InterviewRoom from "./pages/InterviewRoom";

import BotHome from "./bot/pages/BotHome";
import Interview from "./bot/pages/Interview";
import Report from "./bot/pages/Report";
import History from "./bot/pages/History";

import RouteError from "./components/RouteError";
import ProtectedRoute from "./components/ProtectedRoute";
import AuthRedirect from "./components/AuthRedirect";

export const router = createBrowserRouter([
  // PUBLIC
  {
    path: "/",
    Component: Home,
    ErrorBoundary: RouteError,
  },

  // AUTH
  {
    element: <AuthRedirect />,
    ErrorBoundary: RouteError,
    children: [
      {
        path: "/auth",
        Component: Auth,
      },
    ],
  },

  {
    path: "/forgot-password",
    Component: ForgotPassword,
    ErrorBoundary: RouteError,
  },

  {
    path: "/reset-password/:token",
    Component: ResetPassword,
    ErrorBoundary: RouteError,
  },

  // CANDIDATE
  {
    element: <ProtectedRoute allowedRole="CANDIDATE" />,
    ErrorBoundary: RouteError,
    children: [
      {
        path: "/candidate",
        Component: CandidateDashboard,
      },
      {
        path: "/codeeditor",
        Component: CodeEditor,
      },
      {
        path: "/codeeditor/:id",
        Component: CodeEditor,
      },
      {
        path: "/ai-mock",
        Component: BotHome,
      },
      {
        path: "/interview",
        Component: Interview,
      },
      {
        path: "/report/:id",
        Component: Report,
      },
      {
        path: "/history",
        Component: History,
      },
      {
        path: "/report",
        Component: Report,
      },
    ],
  },

  // INTERVIEWER
  {
    element: <ProtectedRoute allowedRole="INTERVIEWER" />,
    ErrorBoundary: RouteError,
    children: [
      {
        path: "/interviewer",
        Component: InterviewerDashboard,
      },
      {
        path: "/interview-room",
        Component: InterviewRoom,
      },
    ],
  },

  // 404
  {
    path: "*",
    Component: RouteError,
  },
]);
