package com.intzdata.product.observaibility.spi.repository;

import com.intzdata.product.observaibility.core.data.entity.LogEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository {
    void save(LogEntity logEntity);

    List<LogEntity> findByLevel(String level);

    List<LogEntity> findAll();

    List<String> findAllLogMessages();

    List<LogEntity> findByTimestampAfter(LocalDateTime timestamp);

    void saveAll(List<LogEntity> logs);
}