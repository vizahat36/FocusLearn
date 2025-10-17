import apiClient from './apiClient';

export const listNotes = (journeyId: string) =>
  apiClient.get(`/journeys/${journeyId}/notes`);

export const createNote = (journeyId: string, payload: any) =>
  apiClient.post(`/journeys/${journeyId}/notes`, payload);