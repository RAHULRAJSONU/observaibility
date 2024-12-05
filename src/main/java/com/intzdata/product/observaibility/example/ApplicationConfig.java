package com.intzdata.product.observaibility.example;

import com.intzdata.product.observaibility.implementations.postgres.JpaExceptionLogRepository;
import com.intzdata.product.observaibility.implementations.postgres.JpaMetricRepository;
import com.intzdata.product.observaibility.implementations.postgres.PostgresExceptionLogRepository;
import com.intzdata.product.observaibility.implementations.postgres.PostgresMetricRepository;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ApplicationConfig {

    @Bean
    @Profile("postgres")
    public MetricRepository postgresMetricRepository(JpaMetricRepository jpaRepository) {
        return new PostgresMetricRepository(jpaRepository);
    }

    @Bean
    @Profile("postgres")
    public ExceptionLogRepository postgresExceptionLogRepository(JpaExceptionLogRepository jpaRepository) {
        return new PostgresExceptionLogRepository(jpaRepository);
    }

}
