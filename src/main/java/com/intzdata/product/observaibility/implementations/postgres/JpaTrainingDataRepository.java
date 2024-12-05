package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.TrainingDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaTrainingDataRepository extends JpaRepository<TrainingDataEntity, Long> {
    List<TrainingDataEntity> findByTimestampAfter(LocalDateTime timestamp);
}