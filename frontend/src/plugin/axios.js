// axios.js
import axios from 'axios';
import useAuthStore from '../stores/auth/authStore.js';

const API_DEV_URL = 'http://localhost:8080';
// const API_SERVER_URL = 'http://localhost:8080';

const instance = axios.create({
  baseURL: API_DEV_URL, // Replace with your API base URL
  withCredentials: true, // Include cookies in requests
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor
instance.interceptors.request.use(async (config) => {
  const { setUserInfo } = useAuthStore.getState();

  if (config.url !== '/api/auth/refresh') {
    try {
      // Call the refresh endpoint
      const response = await axios.get('/refresh', {
        withCredentials: true,
      });

      const { email, role } = response.data;
      console.log(email, role);
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
