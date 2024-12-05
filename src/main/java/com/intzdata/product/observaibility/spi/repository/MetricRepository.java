package com.intzdata.product.observaibility.spi.repository;

import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import com.intzdata.product.observaibility.core.data.models.Metric;

import java.time.LocalDateTime;
import java.util.List;

public interface MetricRepository {
    void save(Metric metric);

    List<Metric> findByName(String name);

    List<Metric> findAll();

    List<MetricEntity> findByTimestampAfter(LocalDateTime lastRetrainTime);

    void saveAll(List<MetricEntity> metrics);
}