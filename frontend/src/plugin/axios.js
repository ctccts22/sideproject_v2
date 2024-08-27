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

  if (config.url !== '/api/auth/refresh') {
    try {
      console.log('Found refreshToken, attempting to refresh.');

      // Call the refresh endpoint
      const response = await instance.get('/api/auth/refresh', {
        withCredentials: true,
      });
      console.log(response.data);
      const { email, role } = response.data;

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
