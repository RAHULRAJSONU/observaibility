package com.intzdata.product.observaibility.spi.repository;

import com.intzdata.product.observaibility.core.data.entity.TrainingDataEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface TrainingDataRepository {
    void save(TrainingDataEntity trainingData);

    List<TrainingDataEntity> findAll();

    List<TrainingDataEntity> findByTimestampAfter(LocalDateTime timestamp);
}