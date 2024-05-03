package com.uobfintech.nftpictures.listener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.uobfintech.nftpictures.service.ContractService;

@Component
public class BlockchainEventListener {

    private final ContractService contractService;

    public BlockchainEventListener(ContractService contractService) {
        this.contractService = contractService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void startListening() {
        contractService.listenToMyEvent();
    }
}

