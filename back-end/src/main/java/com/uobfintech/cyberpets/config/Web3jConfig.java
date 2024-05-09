package com.uobfintech.cyberpets.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {
    private static final String api_key = "69807b14381f41289b95aca892f860f3";

    @Bean
    public Web3j web3j() {
        // 使用应用配置中的节点URL
        return Web3j.build(new HttpService("https://sepolia.infura.io/v3/" +
                api_key));
    }

    @Bean
    public String contractAddressLottery() {
        return "0xCCe5c7e98a79B47E9740D76A61c093667d8dAfAB"; // 合约地址
    }

    @Bean
    public String contractAddressAuction() {
        return "0xa236D20C0959bc699CD6257B286d0612Ce7D2Dc0"; // 合约地址
    }

    @Bean
    public String contractAddressMarket() {
        return "0xdc1784d6720c5f401e9c93D4b80274Bf9ecD39Ac"; // 合约地址
    }

    @Bean
    public String contractAddressNFT() {
        return "0x6E9b5e45D65fE501eda45E16aBbCe5919814946f"; // 合约地址
    }


}

