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
        return "0x36B6d5c27031f98d955E542a6065DE81ab1ca29c"; // 合约地址
    }

    @Bean
    public String contractAddressAuction() {
        return "0xf21bc95e36aD2d12ca2dc6458f29c7b5a2fC36E7"; // 合约地址
    }

    @Bean
    public String contractAddressMarket() {
        return "0x7cEe9662679056Ba92dF6d30CC5428af5a154186"; // 合约地址
    }

    @Bean
    public String contractAddressNFT() {
        return "0x74cfF3F49E329E666E66BCB90d117070A5fd5840"; // 合约地址
    }


}

