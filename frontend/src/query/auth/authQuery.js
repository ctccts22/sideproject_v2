// authQuery.js
import { useMutation, useQuery } from '@tanstack/react-query';
import axios from '../../plugin/axios.js';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../../stores/auth/authStore.js';
import { fetchTest, login, refresh } from './authApi.js';

export const useFetchTest = () => {
  return useQuery({
    queryKey: ['test-query'],
    queryFn: fetchTest,
    staleTime: 60000, // Data is fresh for 1 minute
    refetchOnWindowFocus: true
  });
};

export const useRefreshQuery = () => {
  const { setUserInfo, setIsAuthenticated, setLoading } = useAuthStore();

  return useQuery({
    queryKey: ['refresh-query'],
    queryFn: refresh,
    staleTime: 60000,
    onSuccess: (data) => {
      setUserInfo(data.email, data.role);
      setIsAuthenticated(true);
      setLoading(false);
    },
    onError: () => {
      setIsAuthenticated(false);
      setLoading(false); // Set loading to false if there's an error
    },
  });
};

export const useLoginMutation = () => {
  const navigate = useNavigate();
  const setUserInfo = useAuthStore((state) => state.setUserInfo);
  const setIsAuthenticated = useAuthStore((state) => state.setIsAuthenticated);

  return useMutation({
    mutationFn: login,
    onSuccess: async (data) => {
      if (data.statusCode === '200') {
        console.log('Login success:', data); // Added for debugging

        try {
          const refreshResponse = await axios.get('/api/auth/refresh', {
            withCredentials: true
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
};
