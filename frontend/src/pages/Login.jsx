import React, { useState } from 'react';
import { useLoginMutation } from '../query/auth/authQuery.js';
import useAuthStore from '../stores/auth/authStore.js';

function Login() {
  const { userLoginRequest, setUserLoginRequest, userInfo } = useAuthStore();
  const [email, setEmail] = useState(userLoginRequest.email || '');
  const [password, setPassword] = useState(userLoginRequest.password || '');

  const loginMutation = useLoginMutation();

  const handleSubmit = (e) => {
    e.preventDefault();
    loginMutation.mutate({ email, password });

  };

  if (loginMutation.isLoading) return <p>Loading...</p>;
  if (loginMutation.isError) return <p>Error during login</p>;

  return (
    <div>
      <h1>Login</h1>
      <div>{userInfo.email}</div>
      <div>{userInfo.role}</div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </div>
        <div>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit">Login</button>
        <button type="button">Logout</button>
      </form>
    </div>
  );
}

export default Login
