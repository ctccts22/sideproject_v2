// authQuery.js
import { useMutation, useQuery } from '@tanstack/react-query';
import useAuthStore from '../../stores/auth/authStore.js';
import axios from '../../plugin/axios.js';

export function useLoginMutation() {
  const { setIsAuthentication, setUserInfo } = useAuthStore();

  return useMutation({
    mutationFn: async ({ email, password }) => {
      const response = await axios.post('/api/auth/login', {
        email, password
      });
      return response.data;
    },
    onSuccess: (data) => {
      setUserInfo({ email, role });
      setIsAuthenticated(true);
    },
    onError: (error) => {
      console.error('Login failed:', error);
    }
  })
}


export function useRefreshQuery() {
  const { setUserInfo } = useAuthStore();

  return useQuery({
    queryKey: ['userData'],  // Query key as part of the object
    queryFn: async () => {   // Query function as part of the object
      const response = await axios.get('/api/auth/refresh');
      const { email, role } = response.data;
      setUserInfo({ email, role }); // Set userInfo object with email and role
      return response.data;
    },
    staleTime: 5 * 60 * 1000  // Optional: you can also pass other options here
  });
}


