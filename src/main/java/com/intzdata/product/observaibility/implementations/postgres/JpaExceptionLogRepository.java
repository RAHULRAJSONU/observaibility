package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.ExceptionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaExceptionLogRepository extends JpaRepository<ExceptionLogEntity, Long> {
    List<ExceptionLogEntity> findByType(String type);

    List<ExceptionLogEntity> findByTimestampAfter(LocalDateTime timestamp);
}