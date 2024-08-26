// App.js
// import { Route, Routes, BrowserRouter as Router } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

const queryClient = new QueryClient();

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      {/*<Router>*/}
      {/*  <Routes>*/}
      {/*    <Route path="/" element={<HomePage />} />*/}
      {/*    <Route path="/profile" element={<ProfilePage />} />*/}
      {/*  </Routes>*/}
      {/*</Router>*/}
    </QueryClientProvider>
  );
}

export default App;
