package com.intzdata.product.observaibility.example;

import com.intzdata.product.observaibility.core.service.MetricService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/observability")
public class ObservabilityController {
    private final MetricService metricService;

    public ObservabilityController(MetricService metricService) {
        this.metricService = metricService;
    }

    @PostMapping("/metrics")
    public ResponseEntity<String> addMetric(@RequestParam String name, @RequestParam Double value) {
        metricService.recordMetric(name, value);
        return ResponseEntity.ok("Metric recorded successfully.");
    }
}
