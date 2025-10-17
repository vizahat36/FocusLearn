import React from 'react';
import { useParams } from 'react-router-dom';

const JourneyDetail: React.FC = () => {
  const { id } = useParams();
  return (
    <div>
      <h2>Journey {id}</h2>
      <p>Journey detail and study mode placeholder</p>
    </div>
  );
};

export default JourneyDetail;
