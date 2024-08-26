import React from 'react';
import { useAuthQuery } from '../query/auth/authQuery.js';
import useAuthStore from '../stores/auth/authStore.js';

function Login() {
  const { userInfo } = useAuthStore();
  const { data, isLoading, isError } = useAuthQuery();

  if (isLoading) return <p>Loading...</p>;
  if (isError) return <p>Error loading user data</p>;

  return (
    <div>
      <h1>Profile Page</h1>
      <p>Email: {userInfo.email}</p>
      <p>Role: {userInfo.role}</p>
    </div>
  );
}

export default Login;