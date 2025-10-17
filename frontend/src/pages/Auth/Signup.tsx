import React from 'react';
import AuthForm from '../../components/Auth/AuthForm';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Signup: React.FC = () => {
  const auth = useAuth();
  const nav = useNavigate();

  const handleSignup = async ({ email, password, name }: { email: string; password: string; name?: string }) => {
    await auth.signup(name, email, password);
    nav('/dashboard');
  };

  return (
    <div>
      <AuthForm mode="signup" onSubmit={(c) => handleSignup(c as any)} />
    </div>
  );
};

export default Signup;
