package com.intzdata.product.observaibility.core.service;

import com.intzdata.product.observaibility.core.config.RepositoryFactory;
import com.intzdata.product.observaibility.core.data.builders.MetricBuilder;
import com.intzdata.product.observaibility.core.data.models.Metric;
import com.intzdata.product.observaibility.core.data.observers.ObservabilityNotifier;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MetricService {
    private final MetricRepository metricRepository;
    private final ObservabilityNotifier notifier;

    public MetricService(RepositoryFactory repositoryFactory, ObservabilityNotifier notifier) {
        this.metricRepository = repositoryFactory.getMetricRepository();
        this.notifier = notifier;
    }

    public void recordMetric(String name, Double value) {
        Metric metric = new MetricBuilder()
                .setName(name)
                .setValue(value)
                .setTimestamp(LocalDateTime.now())
                .build();
        metricRepository.save(metric);
        notifier.notifyMetricSaved(metric);
    }
}
