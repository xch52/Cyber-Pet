import * as React from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import MarketPage from "./pages/MarketPage";
import GashPage from "./pages/GashPage";
import PortPage from "./pages/PortPage";
import { useEffect } from 'react';

export default function App() {
  useEffect(() => {
    document.title = "CyberPet";
  }, []); 

  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/Market" element={<MarketPage />} />
        <Route path="/Gashapon" element={<GashPage />} />
        <Route path="/Portfolio" element={<PortPage />} />
      </Routes>
    </Router>
  );
}
