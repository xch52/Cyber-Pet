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
        return "0x134B34D990Cad0bAcac1d22CFACD0ac4Ce2aae20"; // 合约地址
    }

    @Bean
    public String contractAddressAuction() {
        return "0x3F352EE6705d9D79793A290256ce40601090C3df"; // 合约地址
    }

    @Bean
    public String contractAddressMarket() {
        return "0x486717C03b19Df4CF1db10338b14793DBbF16AAA"; // 合约地址
    }

    @Bean
    public String contractAddressNFT() {
        return "0x1096324F09f566eC168C0630CeF649cC7a4578d2"; // 合约地址
    }


}

