package com.uobfintech.cyberpets.listener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.uobfintech.cyberpets.service.ContractService;

@Component
public class BlockchainEventListener {

    private final ContractService contractService;

    public BlockchainEventListener(ContractService contractService) {
        this.contractService = contractService;
    }

    @EventListener(ContextRefreshedEvent.class)
    @Async
    public void startListening() {
        contractService.init();
    }
}

