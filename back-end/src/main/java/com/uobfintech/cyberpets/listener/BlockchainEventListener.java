package com.uobfintech.cyberpets.listener;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.uobfintech.cyberpets.startup.StartupInit;

@Component
public class BlockchainEventListener {

    private final StartupInit startupInit;

    public BlockchainEventListener(StartupInit startupInit) {
        this.startupInit = startupInit;
    }

    @EventListener(ContextRefreshedEvent.class)
    @Async
    public void startListening() {
        startupInit.init();
    }
}

