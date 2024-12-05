package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface JpaLogRepository extends JpaRepository<LogEntity, Long> {

    List<LogEntity> findByLevel(String level);

    List<LogEntity> findByTimestampAfter(LocalDateTime timestamp);
}