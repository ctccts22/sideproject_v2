import useAuthStore from '../stores/auth/authStore.js';
import axios from 'axios';

const API_DEV_URL = 'http://localhost:8080';

const instance = axios.create({
  baseURL: API_DEV_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Request interceptor

instance.interceptors.request.use(async (config) => {
  const { setUserInfo, setIsAuthenticated, setLoading } = useAuthStore.getState();

  if (config.url === '/api/auth/login') {
    return config; // Skip any refresh logic during login
  }

  if (config.url !== '/api/auth/refresh') {
    try {
      setLoading(true);
      console.log('Attempting to refresh token.');

      // Call the refresh endpoint
      const response = await instance.get('/api/auth/refresh', {
        withCredentials: true
      });

      console.log(response.data);
      const { email, role } = response.data;

      setUserInfo(email, role);
      setIsAuthenticated(true);
    } catch (error) {
      console.error('Failed to refresh token:', error.message);
      setIsAuthenticated(false);
    }
  }

  return config;
}, (error) => {
  return Promise.reject(error);
});
export default instance;
