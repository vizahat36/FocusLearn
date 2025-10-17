import React, { useRef } from 'react';
import VideoPlayer, { VideoPlayerHandle } from '../components/Player/VideoPlayer';
import NotesPanel from '../components/Notes/NotesPanel';

const Study: React.FC = () => {
  const playerRef = useRef<any>(null);

  const getCurrentTime = () => {
    try {
      if (playerRef.current && typeof playerRef.current.getCurrentTime === 'function') {
        return playerRef.current.getCurrentTime();
      }
      return 0;
    } catch (e) {
      return 0;
    }
  };

  return (
    <div style={{ display: 'flex', height: '100%' }}>
      <div style={{ flex: 1, padding: 12 }}>
        <h2>Study</h2>
  <VideoPlayer ref={playerRef as any} url="https://www.youtube.com/watch?v=dQw4w9WgXcQ" />
      </div>
      <NotesPanel getCurrentTime={getCurrentTime} />
    </div>
  );
};

export default Study;
