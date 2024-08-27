// store.js
import create from 'zustand';

const useAuthStore = create((set) => ({
  userLoginRequest: {
    email: null,
    password: null
  },
  userInfo: {
    email: null,
    role: null,
  },
  isAuthenticated: false,
  isInitialLogin: false,
  isLoading: true,

  setUserLoginRequest: (email, password) => set({userLoginRequest: {email, password}}),
  setUserInfo: (email, role) => set({ userInfo: { email, role } }),
  setIsAuthenticated: (auth) => set({ isAuthenticated: auth }),
  clearUserInfo: () => set({ userInfo: null, isLoading: false }),
  setInitialLogin: (login) => set({ isInitialLogin: login }),
  setLoading: (loading) => set({ isLoading: loading }),
}));

export default useAuthStore;