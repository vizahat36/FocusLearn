import React, { useState } from 'react';
import { createJourney } from '../api/journeys';
import { useNavigate } from 'react-router-dom';

type StepInput = { title: string; videoId: string };

const JourneyEditor = () => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [steps, setSteps] = useState<StepInput[]>([]);
  const nav = useNavigate();

  const addStep = () => setSteps([...steps, { title: '', videoId: '' }]);
  const updateStep = (idx: number, patch: Partial<StepInput>) => {
    const copy = [...steps];
    copy[idx] = { ...copy[idx], ...patch };
    setSteps(copy);
  };

  const submit = async () => {
    const payload = { title, description, steps };
    const res = await createJourney(payload);
    const id = res.data.id;
    // create steps
    for (let i = 0; i < steps.length; i++) {
      const s = steps[i];
      await fetch(`/api/journeys/${id}/steps`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: s.title, type: 'VIDEO', positionIndex: i }),
      });
    }
    nav(`/journeys/${id}`);
  };

  return (
    <div className="journey-editor">
      <h1>Create Journey</h1>
      <label>Title<input value={title} onChange={(e) => setTitle(e.target.value)} /></label>
      <label>Description<textarea value={description} onChange={(e) => setDescription(e.target.value)} /></label>

      <h3>Steps</h3>
      {steps.map((s, i) => (
        <div key={i} className="step-row">
          <input placeholder="Step title" value={s.title} onChange={(e) => updateStep(i, { title: e.target.value })} />
          <input placeholder="YouTube id or url" value={s.videoId} onChange={(e) => updateStep(i, { videoId: e.target.value })} />
        </div>
      ))}
      <button onClick={addStep}>Add step</button>

      <div style={{ marginTop: 16 }}>
        <button onClick={submit} disabled={!title}>Create</button>
      </div>
    </div>
  );
};

export default JourneyEditor;
