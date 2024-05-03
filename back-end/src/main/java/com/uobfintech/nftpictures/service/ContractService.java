package com.uobfintech.nftpictures.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface ContractService {
    String callContractFunction(@RequestParam String functionName) throws Exception;

    void listenToMyEvent();
}
