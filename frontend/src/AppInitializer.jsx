import React, { useEffect } from 'react';
import axios from './plugin/axios.js';
import useAuthStore from './stores/auth/authStore.js';

const AppInitializer = ({ children }) => {
  const { userInfo, setUserInfo, setIsAuthenticated, setLoading } = useAuthStore();

  useEffect(() => {
    console.log('Effect triggered');
    const fetchUserInfo = async () => {
      try {
        const response = await axios.get('/api/auth/refresh');
        const { email, role } = response.data;
        setUserInfo(email, role);
        setIsAuthenticated(true);
        console.log(userInfo);
      } catch (error) {
        setIsAuthenticated(false);
      } finally {
        setLoading(false);
      }
    };

    fetchUserInfo();
  }, [setUserInfo, setIsAuthenticated, setLoading]);

  return <>{children}</>;
};

export default AppInitializer;
