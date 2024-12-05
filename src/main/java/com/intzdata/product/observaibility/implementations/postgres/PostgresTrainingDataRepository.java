package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.TrainingDataEntity;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostgresTrainingDataRepository implements TrainingDataRepository {

    private final JpaTrainingDataRepository jpaTrainingDataRepository;

    public PostgresTrainingDataRepository(JpaTrainingDataRepository jpaTrainingDataRepository) {
        this.jpaTrainingDataRepository = jpaTrainingDataRepository;
    }

    @Override
    public void save(TrainingDataEntity entity) {
        jpaTrainingDataRepository.save(entity);
    }

    @Override
    public List<TrainingDataEntity> findAll() {
        return jpaTrainingDataRepository.findAll();
    }

    @Override
    public List<TrainingDataEntity> findByTimestampAfter(LocalDateTime timestamp) {
        return jpaTrainingDataRepository.findByTimestampAfter(timestamp);
    }
}
