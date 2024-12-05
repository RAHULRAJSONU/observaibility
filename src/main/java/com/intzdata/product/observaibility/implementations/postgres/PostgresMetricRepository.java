package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import com.intzdata.product.observaibility.core.data.models.Metric;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgresMetricRepository implements MetricRepository {

    private final JpaMetricRepository jpaMetricRepository;

    public PostgresMetricRepository(JpaMetricRepository jpaMetricRepository) {
        this.jpaMetricRepository = jpaMetricRepository;
    }

    @Override
    public void save(Metric metric) {
        MetricEntity entity = new MetricEntity(null, metric.getName(), metric.getValue(), metric.getTimestamp());
        jpaMetricRepository.save(entity);
    }

    @Override
    public List<Metric> findByName(String name) {
        return new ArrayList<>(jpaMetricRepository.findByName(name));
    }

    @Override
    public List<Metric> findAll() {
        return new ArrayList<>(jpaMetricRepository.findAll());
    }

    @Override
    public List<MetricEntity> findByTimestampAfter(LocalDateTime timestamp) {
        return jpaMetricRepository.findByTimestampAfter(timestamp);
    }

    @Override
    public void saveAll(List<MetricEntity> metrics) {
        jpaMetricRepository.saveAll(metrics);
    }
}
