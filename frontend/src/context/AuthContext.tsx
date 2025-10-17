import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react';
import * as authApi from '../api/auth';
import apiClient from '../api/apiClient';

type User = {
  id?: string;
  email: string;
};

type AuthContextType = {
  user: User | null;
  accessToken: string | null;
  refreshToken: string | null;
  login: (email: string, password: string) => Promise<void>;
  signup: (name: string | undefined, email: string, password: string) => Promise<void>;
  logout: () => void;
  refresh: () => Promise<void>;
};

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [accessToken, setAccessToken] = useState<string | null>(localStorage.getItem('auth_token'));
  const [refreshToken, setRefreshToken] = useState<string | null>(localStorage.getItem('refresh_token'));

  useEffect(() => {
    // set Authorization header in apiClient when accessToken changes
    if (accessToken) {
      apiClient.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
    } else {
      delete apiClient.defaults.headers.common['Authorization'];
    }
  }, [accessToken]);

  useEffect(() => {
    // attempt auto-refresh on mount if we have a refresh token
    (async () => {
      if (!accessToken && refreshToken) {
        try {
          await refresh();
        } catch (e) {
          console.warn('refresh failed', e);
          logout();
        }
      }
    })();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const login = async (email: string, password: string) => {
    const res = await authApi.login({ email, password });
    const data = res.data;
    setAccessToken(data.token);
    setRefreshToken(data.refreshToken);
    localStorage.setItem('auth_token', data.token);
    if (data.refreshToken) localStorage.setItem('refresh_token', data.refreshToken);
    // lightweight user extraction from token (subject = email)
    setUser({ email });
  };

  const signup = async (name: string | undefined, email: string, password: string) => {
    const res = await authApi.signup({ name, email, password });
    const data = res.data;
    setAccessToken(data.token);
    setRefreshToken(data.refreshToken);
    localStorage.setItem('auth_token', data.token);
    if (data.refreshToken) localStorage.setItem('refresh_token', data.refreshToken);
    setUser({ email });
  };

  const refresh = async () => {
    if (!refreshToken) throw new Error('no refresh token');
    const res = await authApi.refresh({ refreshToken });
    const data = res.data;
    setAccessToken(data.token);
    localStorage.setItem('auth_token', data.token);
    // keep refresh token the same (backend returns same token)
  };

  const logout = () => {
    setUser(null);
    setAccessToken(null);
    setRefreshToken(null);
    localStorage.removeItem('auth_token');
    localStorage.removeItem('refresh_token');
    try { apiClient.post('/auth/logout'); } catch (_) {}
  };

  return (
    <AuthContext.Provider value={{ user, accessToken, refreshToken, login, signup, logout, refresh }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within AuthProvider');
  return ctx;
};

export default AuthContext;
