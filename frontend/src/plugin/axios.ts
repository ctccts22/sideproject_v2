// src/plugins/axios.ts

import axios from 'axios';
import { API_BASE_URL, API_GET_ACCESS_TOKEN } from '../api/end_point';

const instance = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json'
  }
});

instance.interceptors.request.use(
  async (config) => {
    try {
      await axios.get(API_GET_ACCESS_TOKEN, { withCredentials: true });
    } catch (e) {
      console.error('Error fetching access token:', e);
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default instance;
