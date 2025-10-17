import React from 'react';
import { Link } from 'react-router-dom';

const Header: React.FC = () => {
  return (
    <header style={{padding:'1rem',background:'#fff',boxShadow:'0 1px 4px rgba(0,0,0,0.05)'}}>
      <div className="container" style={{display:'flex',justifyContent:'space-between',alignItems:'center'}}>
        <Link to="/">Focus Learn</Link>
        <nav>
          <Link to="/journeys">Journeys</Link>
          {' | '}
          <Link to="/dashboard">Dashboard</Link>
        </nav>
      </div>
    </header>
  );
};

export default Header;
