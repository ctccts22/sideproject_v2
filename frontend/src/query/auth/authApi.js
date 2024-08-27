import axios from '../../plugin/axios.js';

export const fetchTest = async () => {
  const response = await axios.get('/api/test');
  console.log(response);
  return response.data;
};

export const login = async ({ email, password }) => {
  const response = await axios.post('/api/auth/login', { email, password });
  return response.data;
};

export const refresh = async () => {
  const response = await axios.get('/api/auth/refresh', { withCredentials: true });
  console.log(response);
  return response.data;
};