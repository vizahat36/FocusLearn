import apiClient from './apiClient';

export const authApi = {
  login: (data: any) => apiClient.post('/auth/login', data),
  signup: (data: any) => apiClient.post('/auth/signup', data),
  refresh: () => apiClient.post('/auth/refresh', {}),
  logout: () => apiClient.post('/auth/logout', {}),
};

export const journeyApi = {
  get: (id: string) => apiClient.get(`/journeys/${id}`),
  list: () => apiClient.get('/journeys'),
  create: (data: any) => apiClient.post('/journeys', data),
};

export const notesApi = {
  list: (params?: any) => apiClient.get('/notes', { params }),
  create: (data: any) => apiClient.post('/notes', data),
  update: (id: string, data: any) => apiClient.put(`/notes/${id}`, data),
  delete: (id: string) => apiClient.delete(`/notes/${id}`),
};

export const progressApi = {
  upsert: (data: any) => apiClient.post('/progress', data),
};

export default { authApi, journeyApi, notesApi, progressApi };
