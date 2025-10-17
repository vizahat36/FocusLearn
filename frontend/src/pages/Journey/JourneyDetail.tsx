import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { forkJourney } from '../../api/journeys';
import { useToasts } from '../../components/UI/ToastContext';

const JourneyDetail: React.FC = () => {
  const { id } = useParams();
  const nav = useNavigate();
  const { push } = useToasts();

  const doFork = async () => {
    try {
      const res = await forkJourney(id as string);
      push('Journey forked', 'success');
      nav(`/journeys/${res.data.id}`);
    } catch (e) {
      push('Failed to fork journey', 'error');
    }
  };

  const share = async () => {
    const url = window.location.href;
    if (navigator.share) {
      try { await navigator.share({ title: 'Check out this journey', url }); } catch (e) {}
    } else {
      try { await navigator.clipboard.writeText(url); push('Link copied to clipboard', 'success'); } catch (e) { push('Failed to copy link', 'error'); }
    }
  };

  return (
    <div>
      <h2>Journey {id}</h2>
      <p>Journey detail and study mode placeholder</p>
      <div>
        <button onClick={doFork}>Fork</button>
        <button onClick={share} style={{ marginLeft: 8 }}>Share</button>
      </div>
    </div>
  );
};

export default JourneyDetail;
