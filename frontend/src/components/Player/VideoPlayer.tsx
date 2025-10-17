import React, { useRef, useCallback, forwardRef, useImperativeHandle } from 'react';
import ReactPlayer from 'react-player';

type Props = {
  url?: string;
  youtubeId?: string;
  playing?: boolean;
  controls?: boolean;
  onProgress?: (seconds: number) => void;
  onReady?: () => void;
  className?: string;
};

export type VideoPlayerHandle = {
  getCurrentTime: () => number;
};

const VideoPlayer = forwardRef<VideoPlayerHandle, Props>(({ url, youtubeId, playing = true, controls = true, onProgress, onReady, className }, ref) => {
  const playerRef = useRef<any>(null);

  const source = url ?? (youtubeId ? `https://www.youtube.com/watch?v=${youtubeId}` : undefined);

  const handleProgress = useCallback(
    (state: { played: number; playedSeconds: number; loaded: number; loadedSeconds: number }) => {
      if (onProgress) onProgress(state.playedSeconds);
    },
    [onProgress]
  );

  useImperativeHandle(ref, () => ({
    getCurrentTime: () => {
      try {
        if (playerRef.current && typeof playerRef.current.getCurrentTime === 'function') {
          return playerRef.current.getCurrentTime();
        }
      } catch (e) {
        // ignore
      }
      return 0;
    }
  }));

  return (
    <div className={`video-player ${className || ''}`}>
      {source ? (
        <ReactPlayer
          ref={playerRef}
          url={source}
          playing={playing}
          controls={controls}
          width="100%"
          height="100%"
          onProgress={handleProgress}
          onReady={onReady}
          config={{ youtube: { playerVars: { modestbranding: 1 } } }}
        />
      ) : (
        <div className="video-placeholder">No video</div>
      )}
    </div>
  );
});

export default VideoPlayer;
