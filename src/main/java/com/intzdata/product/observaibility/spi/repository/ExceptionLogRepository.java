package com.intzdata.product.observaibility.spi.repository;

import com.intzdata.product.observaibility.core.data.entity.ExceptionLogEntity;
import com.intzdata.product.observaibility.core.data.models.ExceptionLog;

import java.time.LocalDateTime;
import java.util.List;

public interface ExceptionLogRepository {
    void save(ExceptionLog exceptionLog);

    List<ExceptionLog> findByType(String type);

    List<ExceptionLog> findAll();

    List<String> findAllExceptions();

    List<ExceptionLogEntity> findByTimestampAfter(LocalDateTime lastRetrainTime);

    void saveAll(List<ExceptionLogEntity> exceptions);
}