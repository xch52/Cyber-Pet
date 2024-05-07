// Web3Context.js
import React, { createContext, useState, useContext, useEffect } from 'react';
import Web3 from 'web3';
import PetMarketABI from './abi/PetMarket_abi.json'
import PetAuctionABI from './abi/PetAuction_abi.json'
import PetLotteryABI from './abi/PetLottery_abi.json'
import PetNFTABI from './abi/PetNFT_abi.json'

// 创建 context, 用于全局管理和访问 Web3 功能和智能合约实例
export const Web3Context = createContext(null);

export const Web3Provider = ({ children }) => {
  const [web3, setWeb3] = useState(null);
  const [account, setAccount] = useState('');

    // 使用一个对象来管理所有合约的实例
    const [contracts, setContracts] = useState({
      petMarket: null,
      petAuction: null,
      petLottery: null,
      petNFT: null,
  });

    useEffect(() => {
      // 初始化 Web3 和合约实例
      async function loadWeb3AndContracts() {
        // 用 MetaMask 初始化一个 Web3 实例，并使用此实例创建各个智能合约的实例，地址从环境变量中获取
        if (window.ethereum) {
            const web3Instance = new Web3(window.ethereum);
            setWeb3(web3Instance);

            const contractInstances = {
                petMarket: new web3Instance.eth.Contract(PetMarketABI, process.env.REACT_APP_PETMARKET_ADDRESS),
                petAuction: new web3Instance.eth.Contract(PetAuctionABI, process.env.REACT_APP_PETAUCTION_ADDRESS),
                petLottery: new web3Instance.eth.Contract(PetLotteryABI, process.env.REACT_APP_PETLOTTERY_ADDRESS),
                petNFT: new web3Instance.eth.Contract(PetNFTABI, process.env.REACT_APP_PETNFT_ADDRESS),
            };

      console.log("Contract instances created:", {
        PetMarket: contractInstances.petMarket.options.address,
        PetAuction: contractInstances.petAuction.options.address,
        PetLottery: contractInstances.petLottery.options.address,
        PetNFT: contractInstances.petNFT.options.address,
      });

            setContracts(contractInstances);

            const accounts = await web3Instance.eth.getAccounts();
            if (accounts.length > 0) {
                setAccount(accounts[0]);
            }
        } else {
            console.error("Ethereum object not found. Please install MetaMask or another Ethereum wallet.");
        }
    }

    loadWeb3AndContracts();

    // 设置事件监听器，用于账户和链变化
    const handleAccountsChanged = (accounts) => {
        setAccount(accounts.length > 0 ? accounts[0] : '');
    };

    const handleChainChanged = () => {
        window.location.reload();
    };

    // 监听账户和链的变化
    window.ethereum?.on('accountsChanged', handleAccountsChanged);
    window.ethereum?.on('chainChanged', handleChainChanged);

    return () => {
        // 清理事件监听器
        window.ethereum?.removeListener('accountsChanged', handleAccountsChanged);
        window.ethereum?.removeListener('chainChanged', handleChainChanged);
    };
}, []);

    const connectWallet = async () => {
      if (!window.ethereum) {
        alert('MetaMask is not installed. Please install it to use this app.');
        return;
      }
    
      try {
        const accounts = await window.ethereum.request({ method: 'eth_requestAccounts' });
        if (accounts.length > 0) {
          setAccount(accounts[0]);
        } else {
          alert('No accounts found.');
        }
      } catch (error) {
        console.error("Failed to connect wallet:", error);
        alert("Failed to connect to the wallet. Please make sure MetaMask is unlocked and connected.");
      }
    };
    

  return (
    <Web3Context.Provider value={{
      web3,
      account,
      ...contracts, // 展开contracts对象，直接提供每个合约实例
      connectWallet,
  }}>
      {children}
  </Web3Context.Provider>
  );
};

export const useWeb3 = () => useContext(Web3Context);
