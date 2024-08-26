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

  setUserLoginRequest: (email, password) => set({userLoginRequest: {email, password}}),
  setUserInfo: (email, role) => set({ userInfo: { email, role } }),
  setIsAuthenticated: (auth) => set({ isAuthenticated: auth }),
}));

export default useAuthStore;