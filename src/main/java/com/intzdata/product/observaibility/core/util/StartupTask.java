package com.intzdata.product.observaibility.core.util;

import com.intzdata.product.observaibility.core.service.ObservabilityEnhancementService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupTask implements CommandLineRunner {

    private final ObservabilityEnhancementService enhancementService;

    public StartupTask(ObservabilityEnhancementService enhancementService) {
        this.enhancementService = enhancementService;
    }

    @Override
    public void run(String... args) {
        enhancementService.trainAndEnhanceObservability();
    }
}
