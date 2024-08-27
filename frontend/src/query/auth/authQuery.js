// authQuery.js
import { useMutation } from '@tanstack/react-query';
import axios from '../../plugin/axios.js';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../../stores/auth/authStore.js';

export function useLoginMutation() {
  const navigate = useNavigate();
  const setUserInfo = useAuthStore((state) => state.setUserInfo);
  const setIsAuthenticated = useAuthStore((state) => state.setIsAuthenticated);

  return useMutation({
    mutationFn: async ({ email, password }) => {
      const response = await axios.post('/api/auth/login', { email, password });
      return response.data;
    },
    onSuccess: async (data) => {
      if (data.statusCode === '200') {
        console.log('Login success:', data); // Added for debugging

        try {
          const refreshResponse = await axios.get('/api/auth/refresh', {
            withCredentials: true,
          });

          console.log('Token refreshed:', refreshResponse.data);

          const { email, role } = refreshResponse.data;
          setUserInfo(email, role);
          setIsAuthenticated(true);

        } catch (refreshError) {
          console.error('Failed to refresh token:', refreshError.message);
        }

        navigate('/');
      } else {
        console.error('Unexpected status code:', data.statusCode);
      }
    },
    onError: (error) => {
      console.error('Login failed:', error);
    }
  });
}
