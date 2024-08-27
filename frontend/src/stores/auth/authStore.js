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

  setUserLoginRequest: (email, password) => set({userLoginRequest: {email, password}}),
  setUserInfo: (email, role) => set({ userInfo: { email, role } }),
  setIsAuthenticated: (auth) => set({ isAuthenticated: auth }),
  setInitialLogin: (login) => set({ isInitialLogin: login }),
}));

export default useAuthStore;