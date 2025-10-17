import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Auth/Login';
import Signup from './pages/Auth/Signup';
import Dashboard from './pages/Dashboard';
import JourneyDetail from './pages/Journey/JourneyDetail';
import MyJourneys from './pages/MyJourneys';
import JourneyEditor from './pages/JourneyEditor';
import Study from './pages/Study';
import Import from './pages/Import';
import Profile from './pages/Profile';
import Notes from './pages/Notes';
import Admin from './pages/Admin';
import NotFound from './pages/NotFound';
import PrivateRoute from './components/PrivateRoute';
import { AuthProvider } from './context/AuthContext';
import { ToastProvider } from './components/UI/ToastContext';

function App() {
  return (
    <AuthProvider>
      <ToastProvider>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />

        <Route
          path="/dashboard"
          element={
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          }
        />

        <Route path="/journeys/:id" element={<JourneyDetail />} />
        <Route
          path="/my-journeys"
          element={
            <PrivateRoute>
              <MyJourneys />
            </PrivateRoute>
          }
        />

        <Route
          path="/journeys/:id/edit"
          element={
            <PrivateRoute>
              <JourneyEditor />
            </PrivateRoute>
          }
        />

        <Route
          path="/study/:id"
          element={
            <PrivateRoute>
              <Study />
            </PrivateRoute>
          }
        />

        <Route
          path="/import"
          element={
            <PrivateRoute>
              <Import />
            </PrivateRoute>
          }
        />

        <Route
          path="/profile"
          element={
            <PrivateRoute>
              <Profile />
            </PrivateRoute>
          }
        />

        <Route
          path="/notes"
          element={
            <PrivateRoute>
              <Notes />
            </PrivateRoute>
          }
        />

        <Route
          path="/admin"
          element={
            <PrivateRoute>
              <Admin />
            </PrivateRoute>
          }
        />

        <Route path="*" element={<NotFound />} />
      </Routes>
      </ToastProvider>
    </AuthProvider>
  );
}

export default App;
