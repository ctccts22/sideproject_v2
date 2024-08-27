import React from 'react';
import { Navigate } from 'react-router-dom';
import useAuthStore from '../stores/auth/authStore.js';

const ProtectedRoute = ({ element, allowedRoles, redirectTo = '/login' }) => {
  const { userInfo, isAuthenticated, isLoading } = useAuthStore();
  console.log(userInfo);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  const isAuthorized = allowedRoles.includes(userInfo?.role);

  return isAuthenticated && isAuthorized ? element : <Navigate to={redirectTo} />;
};

export default ProtectedRoute;
