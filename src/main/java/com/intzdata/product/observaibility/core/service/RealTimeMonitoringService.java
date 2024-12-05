package com.intzdata.product.observaibility.core.service;

import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import org.springframework.stereotype.Component;

@Component
public class RealTimeMonitoringService {

    private final ObservabilityEnhancementService enhancementService;

    public RealTimeMonitoringService(ObservabilityEnhancementService enhancementService) {
        this.enhancementService = enhancementService;
    }

    public void monitorMetrics(MetricEntity metric) {
        if (enhancementService.detectAnomaly(metric)) {
            System.out.println("Anomaly detected in metric: " + metric.getName());
            // Send alert (e.g., email, SMS, etc.)
        }
    }
}
