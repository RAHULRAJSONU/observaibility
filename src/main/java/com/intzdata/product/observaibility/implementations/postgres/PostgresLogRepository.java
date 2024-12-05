package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import com.intzdata.product.observaibility.spi.repository.LogRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgresLogRepository implements LogRepository {

    private final JpaLogRepository jpaLogRepository;

    public PostgresLogRepository(JpaLogRepository jpaLogRepository) {
        this.jpaLogRepository = jpaLogRepository;
    }

    @Override
    public void save(LogEntity logEntity) {
        jpaLogRepository.save(logEntity);
    }

    @Override
    public List<LogEntity> findByLevel(String level) {
        return new ArrayList<>(jpaLogRepository.findByLevel(level));
    }

    @Override
    public List<LogEntity> findAll() {
        return new ArrayList<>(jpaLogRepository.findAll());
    }

    @Override
    public List<String> findAllLogMessages() {
        return jpaLogRepository.findAll().stream().map(LogEntity::getMessage).toList();
    }

    @Override
    public List<LogEntity> findByTimestampAfter(LocalDateTime timestamp) {
        return jpaLogRepository.findByTimestampAfter(timestamp);
    }

    @Override
    public void saveAll(List<LogEntity> logs) {
        jpaLogRepository.saveAll(logs);
    }

}
