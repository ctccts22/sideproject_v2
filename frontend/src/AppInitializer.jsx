import React, { useEffect } from 'react';
import { useRefreshQuery } from './query/auth/authQuery.js';
import useAuthStore from './stores/auth/authStore.js';

const AppInitializer = ({ children }) => {
  const { isLoading, setLoading } = useAuthStore();
  const refetch = useRefreshQuery();

  useEffect(() => {
    // Trigger the refresh query when the app starts
    refetch();
  }, [refetch]);

  if (isLoading) {
    return <div>Loading...</div>; // Render a loading indicator while the app is initializing
  }

  return <>{children}</>; // Render the children (the app) once initialization is complete
};

export default AppInitializer;
