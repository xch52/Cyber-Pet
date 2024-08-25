package com.uobfintech.cyberpets.startup;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uobfintech.cyberpets.service.ContractService;

@Slf4j
@Component
public class StartupInit {
    @Autowired
    private ContractService contractService;

    @PostConstruct
    public void init() {
        log.info("Subscribing to events...");
        contractService.subscribeToPetMinted();
        contractService.subscribeToLotteryRequestedEvent();
        contractService.subscribeToLotteryFulfilledEvent();
        contractService.subscribeToPetListedEvent();
        contractService.subscribeToPetSoldEvent();
        contractService.subscribeToSaleCancelledEvent();
        contractService.subscribeToAuctionCreatedEvent();
        contractService.subscribeToBidPlacedEvent();
        contractService.subscribeToAuctionEndedEvent();
    }
}
