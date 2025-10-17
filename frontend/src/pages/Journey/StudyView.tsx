import React from 'react';
import VideoPlayer from '../../components/Player/VideoPlayer';

const StudyView: React.FC = () => {
  const videoId = 'dQw4w9WgXcQ';
  return (
    <div style={{display:'grid',gridTemplateColumns:'2fr 1fr',gap:'1rem',height:'80vh'}}>
      <div style={{background:'#000'}}>
        <VideoPlayer youtubeId={videoId} />
      </div>
      <aside style={{background:'#fff',padding:'1rem'}}>
        <h3>Notes</h3>
        <p>Time stamped notes will appear here</p>
      </aside>
    </div>
  );
};

export default StudyView;