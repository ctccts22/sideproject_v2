// axios.js
import axios from 'axios';
import useAuthStore from '../stores/auth/authStore.js';

const instance = axios.create({
  baseURL: API_BASE_URL, // Replace with your API base URL
  withCredentials: true, // Include cookies in requests
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor
instance.interceptors.request.use(async (config) => {
  const { setUserInfo } = useAuthStore.getState();

  if (config.url !== '/api/refresh') {
    try {
      // Call the refresh endpoint
      const response = await axios.get('/api/refresh', {
        withCredentials: true,
      });

      const { email, role } = response.data;

      setUserInfo(email, role);
    } catch (error) {
      console.error('Failed to refresh token:', error);
    }
  }

  return config;
}, (error) => {
  return Promise.reject(error);
});

export default instance;
