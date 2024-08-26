// authQuery.js
import { useMutation } from '@tanstack/react-query';
import useAuthStore from '../../stores/auth/authStore.js';
import axios from '../../plugin/axios.js';

export function useLoginMutation() {
  const { setIsAuthenticated, setUserInfo } = useAuthStore();

  return useMutation({
    mutationFn: async ({ email, password }) => {
      const response = await axios.post('/api/auth/login', { email, password });
      return response.data;
    },
    onSuccess: (data) => {
      // Assuming the response contains email and role
      const { email, role } = data;
      setUserInfo(email, role);
      setIsAuthenticated(true);
      console.log('Login success');
    },
    onError: (error) => {
      console.error('Login failed:', error);
    }
  });
}
