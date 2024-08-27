// ProtectedRoute.js
import React from 'react';
import { Navigate } from 'react-router-dom';
import useAuthStore from '../stores/auth/authStore.js';


const ProtectedRoute = ({ element, allowedRoles, redirectTo = '/login' }) => {
  const { userInfo } = useAuthStore();

  const isAuthorized = allowedRoles.includes(userInfo?.role);

  return isAuthorized ? element : <Navigate to={redirectTo} />;
};

export default ProtectedRoute;
