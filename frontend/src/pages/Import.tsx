import React, { useState } from 'react';
import { importPlaylist } from '../api/journeys';
import { useNavigate } from 'react-router-dom';

const Import = () => {
  const [url, setUrl] = useState('');
  const [title, setTitle] = useState('');
  const nav = useNavigate();

  const submit = async () => {
    const res = await importPlaylist(url, title || undefined);
    nav(`/journeys/${res.data.id}`);
  };

  return (
    <div>
      <h1>Import playlist</h1>
      <label>Playlist URL<input value={url} onChange={(e) => setUrl(e.target.value)} /></label>
      <label>Optional title<input value={title} onChange={(e) => setTitle(e.target.value)} /></label>
      <button onClick={submit} disabled={!url}>Import</button>
    </div>
  );
};

export default Import;
