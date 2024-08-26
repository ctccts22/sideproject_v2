import axios from 'axios';
import useAuthStore from '../stores/auth/authStore.js';
import Cookies from 'js-cookie';

const API_DEV_URL = 'http://localhost:8080';

const instance = axios.create({
  baseURL: API_DEV_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor
instance.interceptors.request.use(async (config) => {
  const { setUserInfo } = useAuthStore.getState();

  // Check if the "refreshToken" cookie is present
  const refreshToken = Cookies.get('refreshToken');

  if (config.url !== '/api/auth/refresh') {
    try {
      console.log('Found refreshToken, attempting to refresh.');

      // Call the refresh endpoint
      const response = await axios.get('/api/auth/refresh', {
        withCredentials: true,
      });

      const { email, role } = response.data;
      console.log('Refreshed token data:', email, role);
      setUserInfo(email, role);
    } catch (error) {
      console.error('Failed to refresh token:', error.message);
    }
  } else {
    console.log('No refreshToken found or current request is refresh token request.');
  }

  return config;
}, (error) => {
  return Promise.reject(error);
});

export default instance;
