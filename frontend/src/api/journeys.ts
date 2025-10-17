import apiClient from './apiClient';

export const listJourneys = (params?: any) =>
  apiClient.get('/journeys', { params });

export const getJourney = (id: string) =>
  apiClient.get(`/journeys/${id}`);

export const createJourney = (payload: any) =>
  apiClient.post('/journeys', payload);

export const forkJourney = (id: string) =>
  apiClient.post(`/journeys/${id}/fork`);

export const importPlaylist = (url: string, title?: string) =>
  apiClient.post(`/journeys/import?url=${encodeURIComponent(url)}${title ? `&title=${encodeURIComponent(title)}` : ''}`);