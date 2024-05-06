import * as React from 'react';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import AuctionMarket from "./pages/AuctionMarket";
import FreeMarket from "./pages/FreeMarket";
import GashPage from "./pages/GashPage";
import PortPage from "./pages/PortPage";
import History from "./pages/History";
import { useEffect } from 'react';
import { Web3Provider  } from './Web3Context';

export default function App() {
  useEffect(() => {
    document.title = "CyberPet";
  }, []); 

  //console.log("PetMarket Address:", process.env.REACT_APP_PETMARKET_ADDRESS);

  return (
    <Web3Provider>  {/* 使用 Web3Provider 包裹整个应用 */}
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/AuctionMarket" element={<AuctionMarket />} />
          <Route path="/FreeMarket" element={<FreeMarket />} />
          <Route path="/Gashapon" element={<GashPage />} />
          <Route path="/History" element={<History />} />
          <Route path="/Portfolio" element={<PortPage />} />
        </Routes>
      </Router>
    </Web3Provider>
  );
}
