// App.js
import { Route, Routes, BrowserRouter as Router } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import Login from './pages/Login.jsx';
import HomePage from './pages/HomePage.jsx';
import ProtectedRoute from './routes/ProtectRoute.jsx';
import Example from './pages/Example.jsx';

const queryClient = new QueryClient();

function App() {

  return (
    <QueryClientProvider client={queryClient}>
      <Router>
        <Routes>
          <Route path="/example" element={<ProtectedRoute element={<Example />} allowedRoles={['SUPER']} />} />
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </Router>
    </QueryClientProvider>
  );
}

export default App;
