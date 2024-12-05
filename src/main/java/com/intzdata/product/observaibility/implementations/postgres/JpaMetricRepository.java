package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaMetricRepository extends JpaRepository<MetricEntity, Long> {
    List<MetricEntity> findByName(String name);

    List<MetricEntity> findByTimestampAfter(LocalDateTime timestamp);
}