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
        return "0x83766F5D7F91aFc7DF615A69edBFdD77DDaFd46f"; // 合约地址
    }

    @Bean
    public String contractAddressAuction() {
        return "0xaa4841b0D5A461A01F71876941ab719eb0aB9a42"; // 合约地址
    }

    @Bean
    public String contractAddressMarket() {
        return "0xa31794e9f663D60E45a9998C783368DCfF184785"; // 合约地址
    }

    @Bean
    public String contractAddressNFT() {
        return "0x3BD54DD1eB5CFA6d99cBBE11281eFc10912A0e95"; // 合约地址
    }


}

