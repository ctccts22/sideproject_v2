import axios from 'axios';

const API_DEV_URL = 'http://localhost:8080';

const instance = axios.create({
  baseURL: API_DEV_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Optionally, you can keep other interceptors for handling request/response globally
instance.interceptors.response.use(
  response => response,
  error => {
    // Handle errors
    return Promise.reject(error);
  }
);

export default instance;
