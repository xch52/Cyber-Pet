package com.uobfintech.nftpictures.config;

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
        return "0xCF44690c7B05407efd3aDa3B68cA254C17216b4B"; // 合约地址
    }

    @Bean
    public String contractAddressAuction() {
        return "0x23CDa6646B85a798604d74E782a623CFe242402D"; // 合约地址
    }

    @Bean
    public String contractAddressMarket() {
        return "0x53Bd31d4E276F4d1A629311759d32D153415ec07"; // 合约地址
    }


}

