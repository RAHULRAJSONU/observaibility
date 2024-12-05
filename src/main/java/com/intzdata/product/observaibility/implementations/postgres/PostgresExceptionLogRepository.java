package com.intzdata.product.observaibility.implementations.postgres;

import com.intzdata.product.observaibility.core.data.entity.ExceptionLogEntity;
import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgresExceptionLogRepository implements ExceptionLogRepository {

    private final JpaExceptionLogRepository jpaExceptionLogRepository;

    public PostgresExceptionLogRepository(JpaExceptionLogRepository jpaExceptionLogRepository) {
        this.jpaExceptionLogRepository = jpaExceptionLogRepository;
    }

    @Override
    public void save(ExceptionLog exceptionLog) {
        ExceptionLogEntity entity = new ExceptionLogEntity(
                null,
                exceptionLog.getType(),
                exceptionLog.getMessage(),
                exceptionLog.getStackTrace(),
                exceptionLog.getTimestamp()
        );
        jpaExceptionLogRepository.save(entity);
    }

    @Override
    public List<ExceptionLog> findByType(String type) {
        return new ArrayList<>(jpaExceptionLogRepository.findByType(type));
    }

    @Override
    public List<ExceptionLog> findAll() {
        return new ArrayList<>(jpaExceptionLogRepository.findAll());
    }

    @Override
    public List<String> findAllExceptions() {
        return jpaExceptionLogRepository.findAll().stream().map(ExceptionLog::getStackTrace).toList();
    }

    @Override
    public List<ExceptionLogEntity> findByTimestampAfter(LocalDateTime lastRetrainTime) {
        return jpaExceptionLogRepository.findByTimestampAfter(lastRetrainTime);
    }

    @Override
    public void saveAll(List<ExceptionLogEntity> exceptions) {
        jpaExceptionLogRepository.saveAll(exceptions);
    }

}
