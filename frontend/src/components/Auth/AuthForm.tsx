import React, { useState, PropsWithChildren } from 'react';

type Credentials = {
  email: string;
  password: string;
  name?: string;
};

type Props = {
  mode?: 'login' | 'signup';
  submitLabel?: string;
  onSubmit: (creds: Credentials) => Promise<any>;
};

const AuthForm: React.FC<Props> = ({ mode = 'login', submitLabel, onSubmit, children }: PropsWithChildren<Props>) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const label = submitLabel ?? (mode === 'login' ? 'Log in' : 'Sign up');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setLoading(true);
    try {
      await onSubmit({ email, password, name: mode === 'signup' ? name : undefined });
    } catch (err: any) {
      setError(err?.response?.data?.message || err?.message || 'Unexpected error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form className="auth-form" onSubmit={handleSubmit}>
      <h3>{mode === 'login' ? 'Welcome back' : 'Create account'}</h3>

      {mode === 'signup' && (
        <label>
          Name
          <input value={name} onChange={(e) => setName(e.target.value)} required />
        </label>
      )}

      <label>
        Email
        <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
      </label>

      <label>
        Password
        <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
      </label>

      {error && <div className="auth-error">{error}</div>}

      <button type="submit" disabled={loading}>
        {loading ? 'Please wait…' : label}
      </button>

      {children}
    </form>
  );
};

export default AuthForm;
