package com.intzdata.product.observaibility.core.config;

import com.intzdata.product.observaibility.implementations.postgres.PostgresMetricRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {
    @Value("${observability.repository.metric}")
    private String metricRepositoryType;

    @Autowired
    private ApplicationContext context;

    public MetricRepository getMetricRepository() {
        if ("postgres".equalsIgnoreCase(metricRepositoryType)) {
            return context.getBean(PostgresMetricRepository.class);
        }
        // Add more repository types as needed
        throw new IllegalArgumentException("Unsupported repository type");
    }
}
