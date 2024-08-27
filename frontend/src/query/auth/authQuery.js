// authQuery.js
import { useMutation } from '@tanstack/react-query';
import axios from '../../plugin/axios.js';

export function useLoginMutation() {

  return useMutation({
    mutationFn: async ({ email, password }) => {
      const response = await axios.post('/api/auth/login', { email, password });
      return response.data;
    },
    onSuccess: (data) => {
      // Assuming the response contains email and role
      console.log('Login success');
    },
    onError: (error) => {
      console.error('Login failed:', error);
    }
  });
}
