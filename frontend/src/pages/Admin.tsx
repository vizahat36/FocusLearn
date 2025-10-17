import React, { useEffect, useState } from 'react';
import apiClient from '../api/apiClient';
import { useToasts } from '../components/UI/ToastContext';

const Admin: React.FC = () => {
  const [users, setUsers] = useState<any[]>([]);
  const [journeys, setJourneys] = useState<any[]>([]);
  const { push } = useToasts();

  const load = async () => {
    try {
      const u = await apiClient.get('/admin/users');
      setUsers(u.data || []);
      const j = await apiClient.get('/admin/journeys');
      setJourneys(j.data || []);
    } catch (e: any) {
      push('Failed to load admin data', 'error');
    }
  };

  useEffect(() => { load(); }, []);

  const deleteUser = async (id: string) => {
    try {
      await apiClient.delete(`/admin/users/${id}`);
      setUsers(prev => prev.filter(u => u.id !== id));
      push('User deleted', 'success');
    } catch (e) { push('Failed to delete user', 'error'); }
  };

  const deleteJourney = async (id: string) => {
    try {
      await apiClient.delete(`/admin/journeys/${id}`);
      setJourneys(prev => prev.filter(j => j.id !== id));
      push('Journey deleted', 'success');
    } catch (e) { push('Failed to delete journey', 'error'); }
  };

  return (
    <div style={{ padding: 12 }}>
      <h1>Admin</h1>
      <section>
        <h2>Users</h2>
        <ul>
          {users.map(u => (
            <li key={u.id}>
              {u.email} ({u.name})
              <button onClick={() => deleteUser(u.id)} style={{ marginLeft: 8 }}>Delete</button>
            </li>
          ))}
        </ul>
      </section>
      <section>
        <h2>Journeys</h2>
        <ul>
          {journeys.map(j => (
            <li key={j.id}>
              {j.title}
              <button onClick={() => deleteJourney(j.id)} style={{ marginLeft: 8 }}>Delete</button>
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
};

export default Admin;
