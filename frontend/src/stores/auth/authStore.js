// store.js
import create from 'zustand';

const useAuthStore = create((set) => ({
  userInfo: {
    email: null,
    role: null,
  },
  isAuthenticated: false,

  setUserInfo: (email, role) => set({ userInfo: { email, role } }),
  setIsAuthenticated: (auth) => set({ isAuthenticated: auth }),
}));

export default useAuthStore;