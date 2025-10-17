import React from 'react';

type JourneySummary = {
  id: string;
  title: string;
  description?: string;
  thumbnailUrl?: string;
  ownerName?: string;
  stepsCount?: number;
  durationSec?: number;
};

type Props = {
  journey: JourneySummary;
  onOpen: (id: string) => void;
  onEdit?: (id: string) => void;
  onFork?: (id: string) => void;
};

const formatDuration = (s?: number) => {
  if (!s) return '';
  const mins = Math.floor(s / 60);
  return `${mins} min`;
};

const JourneyCard: React.FC<Props> = ({ journey, onOpen, onEdit, onFork }) => {
  return (
    <article className="journey-card" role="article">
      <div className="thumb" onClick={() => onOpen(journey.id)}>
        {journey.thumbnailUrl ? (
          <img src={journey.thumbnailUrl} alt={`${journey.title} thumbnail`} />
        ) : (
          <div className="thumb-placeholder" />
        )}
      </div>
      <div className="meta">
        <h4 className="title" onClick={() => onOpen(journey.id)}>{journey.title}</h4>
        <p className="desc">{journey.description}</p>
        <div className="info">
          <span>{journey.ownerName}</span>
          <span>{journey.stepsCount ?? 0} steps</span>
          <span>{formatDuration(journey.durationSec)}</span>
        </div>
      </div>
      <div className="actions">
        {onFork && <button onClick={() => onFork(journey.id)}>Fork</button>}
        {onEdit && <button onClick={() => onEdit(journey.id)}>Edit</button>}
        <button onClick={() => onOpen(journey.id)}>Open</button>
      </div>
    </article>
  );
};

export default JourneyCard;