import apiClient from './apiClient';

export const login = (payload: { email: string; password: string }) =>
  apiClient.post('/auth/login', payload);

export const signup = (payload: { name?: string; email: string; password: string }) =>
  apiClient.post('/auth/signup', payload);

export const refresh = (payload: { refreshToken: string }) =>
  apiClient.post('/auth/refresh', payload);

export const logout = () => apiClient.post('/auth/logout');