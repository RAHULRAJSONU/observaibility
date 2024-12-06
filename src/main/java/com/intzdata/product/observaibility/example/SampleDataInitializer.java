package com.intzdata.product.observaibility.example;

import com.intzdata.product.observaibility.core.data.entity.ExceptionLogEntity;
import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import com.intzdata.product.observaibility.spi.repository.LogRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class SampleDataInitializer {

    private final LogRepository logRepository;
    private final MetricRepository metricRepository;
    private final ExceptionLogRepository exceptionLogRepository;

    @Autowired
    public SampleDataInitializer(LogRepository logRepository, MetricRepository metricRepository, ExceptionLogRepository exceptionLogRepository) {
        this.logRepository = logRepository;
        this.metricRepository = metricRepository;
        this.exceptionLogRepository = exceptionLogRepository;
    }

    //    @PostConstruct
    public void addSampleLogs() {
        String eXMessage = """
                {"message": "Application started successfully."}
                """;
        List<LogEntity> sampleLogs = List.of(
                new LogEntity(null, "Observality.service", "INFO", eXMessage, LocalDateTime.now()),
                new LogEntity(null, "Observality.service", "ERROR", eXMessage, LocalDateTime.now().minusMinutes(10)),
                new LogEntity(null, "Observality.service", "WARN", eXMessage, LocalDateTime.now().minusHours(1))
        );

        logRepository.saveAll(sampleLogs);
        log.info("Sample logs added to the database.");
        List<ExceptionLogEntity> sampleExceptions = List.of(
                new ExceptionLogEntity(null, "NullPointerException", "Null value encountered.", "com.example.MyClass.method(MyClass.java:25)", LocalDateTime.now()),
                new ExceptionLogEntity(null, "IOException", "File not found.", "com.example.FileService.readFile(FileService.java:15)", LocalDateTime.now().minusMinutes(5)),
                new ExceptionLogEntity(null, "IllegalArgumentException", "Invalid argument provided.", "com.example.Validator.validate(Validator.java:10)", LocalDateTime.now().minusHours(1))
        );

        exceptionLogRepository.saveAll(sampleExceptions);
        log.info("Sample exceptions added to the database.");
        List<MetricEntity> sampleMetrics = List.of(
                new MetricEntity(null, "CPU_Usage", 45.6, LocalDateTime.now()),
                new MetricEntity(null, "Memory_Usage", 78.2, LocalDateTime.now().minusMinutes(10)),
                new MetricEntity(null, "Disk_Usage", 90.1, LocalDateTime.now().minusHours(1))
        );

        metricRepository.saveAll(sampleMetrics);
        log.info("Sample metrics added to the database.");
    }
}
