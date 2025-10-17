import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import Admin from '../Admin';
import { ToastProvider } from '../../components/UI/ToastContext';

// mock apiClient
jest.mock('../../api/apiClient', () => ({
  get: (path: string) => {
    if (path === '/admin/users') return Promise.resolve({ data: [{ id: 'u1', email: 'a@b.com', name: 'A' }] });
    if (path === '/admin/journeys') return Promise.resolve({ data: [{ id: 'j1', title: 'J1' }] });
    return Promise.resolve({ data: [] });
  },
  delete: () => Promise.resolve({}),
}));

describe('Admin page', () => {
  it('renders users and journeys', async () => {
    render(
      <ToastProvider>
        <Admin />
      </ToastProvider>
    );

    await waitFor(() => expect(screen.getByText(/Users/i)).toBeInTheDocument());

    expect(screen.getByText('a@b.com (A)')).toBeInTheDocument();
    expect(screen.getByText('J1')).toBeInTheDocument();
  });
});
