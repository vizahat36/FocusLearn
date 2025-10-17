import React, { useEffect, useState } from 'react';
import { notesApi, progressApi } from '../../api';

type Note = {
  id?: string;
  userId?: string;
  journeyId?: string;
  stepId?: string;
  content?: string;
  timestampSeconds?: number;
};

const NotesPanel: React.FC<{ journeyId?: string; stepId?: string; getCurrentTime: () => number }> = ({ journeyId, stepId, getCurrentTime }) => {
  const [notes, setNotes] = useState<Note[]>([]);
  const [newContent, setNewContent] = useState('');

  const load = async () => {
    const params: any = {};
    if (stepId) params.stepId = stepId;
    else if (journeyId) params.journeyId = journeyId;
    const res = await notesApi.list(params);
    setNotes(res.data || []);
  };

  useEffect(() => { load(); }, [stepId, journeyId]);

  const createNote = async () => {
    const ts = Math.floor(getCurrentTime());
    const payload = { journeyId, stepId, content: newContent, timestampSeconds: ts };
    const res = await notesApi.create(payload);
    setNewContent('');
    setNotes(prev => [res.data, ...prev]);
    // also send a small progress update
    try { await progressApi.upsert({ journeyId, stepId, lastPositionSeconds: ts, deltaTimeSpentSeconds: 0 }); } catch (e) {}
  };

  const remove = async (id?: string) => {
    if (!id) return;
    await notesApi.delete(id);
    setNotes(prev => prev.filter(n => n.id !== id));
  };

  const exportNotes = async () => {
    if (!journeyId) return;
    const res = await notesApi.list({ journeyId });
    // fallback: call backend export endpoint to get attachment
    try {
      const resp = await fetch(`/api/notes/export?journeyId=${journeyId}`, { headers: { Accept: 'text/markdown' } });
      const text = await resp.text();
      const blob = new Blob([text], { type: 'text/markdown' });
      const url = URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = `notes_${journeyId}.md`;
      document.body.appendChild(a);
      a.click();
      a.remove();
      URL.revokeObjectURL(url);
    } catch (e) {
      console.error(e);
    }
  };

  return (
    <div style={{ padding: 12, width: 360, borderLeft: '1px solid #eee' }}>
      <h3>Notes</h3>
      <div>
        <textarea value={newContent} onChange={e => setNewContent(e.target.value)} rows={3} style={{ width: '100%' }} />
        <button onClick={createNote} disabled={!newContent.trim()}>Add note at {Math.floor(getCurrentTime())}s</button>
      </div>
      <div style={{ marginTop: 12 }}>
        <div style={{ marginBottom: 8 }}>
          <button onClick={exportNotes} disabled={!journeyId}>Export notes (.md)</button>
        </div>
        {notes.map(n => (
          <div key={n.id} style={{ padding: 8, borderBottom: '1px solid #f0f0f0' }}>
            <div style={{ fontSize: 12, color: '#666' }}>{n.timestampSeconds}s</div>
            <div style={{ whiteSpace: 'pre-wrap' }}>{n.content}</div>
            <div style={{ marginTop: 6 }}>
              <button onClick={() => { /* TODO: edit inline later */ }}>Edit</button>
              <button onClick={() => remove(n.id)} style={{ marginLeft: 8 }}>Delete</button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default NotesPanel;
