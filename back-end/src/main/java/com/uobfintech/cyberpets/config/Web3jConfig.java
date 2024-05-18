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
        return "0xBBFb134bc72BD53451e19633aDb3ad898B689BF4"; // 合约地址
    }

    @Bean
    public String contractAddressAuction() {
        return "0xa7a2FB6365864563B1b29B7F9789944D6bB54667"; // 合约地址
    }

    @Bean
    public String contractAddressMarket() {
        return "0x37878F561251F8B8A7C8C1b4E47eF5A85e1bc7C5"; // 合约地址
    }

    @Bean
    public String contractAddressNFT() {
        return "0x8E3f51dfab40D7d00418C1885e5d484267602f3E"; // 合约地址
    }


}

