// routesConfig.js
import HomePage from '../pages/HomePage';
import Login from '../pages/Login';
import NotFoundPage from '../pages/NotFoundPage';

const routesConfig = [
  { path: '/login', element: <Login />, allowedRoles: [] }, // No protection
  { path: '/not-found', element: <NotFoundPage />, allowedRoles: [] }, // Example page
  { path: '/', element: <HomePage />, allowedRoles: ['SUPER'], redirectTo: '/not-found' }
];

export default routesConfig;
