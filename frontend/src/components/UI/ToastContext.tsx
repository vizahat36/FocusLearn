import React, { createContext, useContext, useState } from 'react';

type Toast = { id: string; message: string; type?: 'info' | 'success' | 'error' };

const ToastsContext = createContext<any>(null);

export const useToasts = () => useContext(ToastsContext);

export const ToastProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [toasts, setToasts] = useState<Toast[]>([]);

  const push = (message: string, type: Toast['type'] = 'info') => {
    const t = { id: String(Date.now()) + Math.random(), message, type };
    setToasts(prev => [t, ...prev]);
    setTimeout(() => setToasts(prev => prev.filter(x => x.id !== t.id)), 4000);
  };

  return (
    <ToastsContext.Provider value={{ push }}>
      {children}
      <div style={{ position: 'fixed', right: 12, top: 12, zIndex: 9999 }}>
        {toasts.map(t => (
          <div key={t.id} style={{ marginBottom: 8, padding: 8, borderRadius: 4, background: t.type === 'error' ? '#ffdddd' : t.type === 'success' ? '#ddffdd' : '#ffffff', boxShadow: '0 1px 3px rgba(0,0,0,0.1)' }}>
            {t.message}
          </div>
        ))}
      </div>
    </ToastsContext.Provider>
  );
};

export default ToastsContext;
