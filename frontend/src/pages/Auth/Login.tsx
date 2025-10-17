import React from 'react';
import AuthForm from '../../components/Auth/AuthForm';
import { useAuth } from '../../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Login: React.FC = () => {
  const auth = useAuth();
  const nav = useNavigate();

  const handleLogin = async ({ email, password }: { email: string; password: string }) => {
    await auth.login(email, password);
    nav('/dashboard');
  };

  return (
    <div>
      <AuthForm mode="login" onSubmit={(c) => handleLogin(c as any)} />
    </div>
  );
};

export default Login;
