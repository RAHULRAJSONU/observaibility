package com.intzdata.product.observaibility.core.service;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.entity.ExceptionLogEntity;
import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import com.intzdata.product.observaibility.core.model.ModelTrainingService;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackLoopService {

    private final MetricRepository metricRepository;
    private final ExceptionLogRepository exceptionLogRepository;
    private final ModelTrainingService modelTrainingService;

    public FeedbackLoopService(MetricRepository metricRepository,
                               ExceptionLogRepository exceptionLogRepository,
                               ModelTrainingService modelTrainingService) {
        this.metricRepository = metricRepository;
        this.exceptionLogRepository = exceptionLogRepository;
        this.modelTrainingService = modelTrainingService;
    }

    @Scheduled(fixedRateString = "${feedback.loop.interval:60000}") // Default 60 seconds
    public void runFeedbackLoop() {
        System.out.println("Running feedback loop...");

        // Collect new data
        List<MetricEntity> newMetrics = collectNewMetrics();
        List<ExceptionLogEntity> newLogs = collectNewLogs();

        // Log the number of new data points
        System.out.printf("Collected %d new metrics and %d new logs.%n", newMetrics.size(), newLogs.size());

        // Retrain models with new data
        if (!newMetrics.isEmpty()) {
            modelTrainingService.trainModel(ModelConfig.METRICS_MODEL.getName()); // Retrain metrics model
        }
        if (!newLogs.isEmpty()) {
            modelTrainingService.trainModel(ModelConfig.LOGS_MODEL.getName()); // Retrain logs model
        }

        System.out.println("Feedback loop completed.");
    }

    private List<MetricEntity> collectNewMetrics() {
        // Example logic: Fetch metrics created in the last N minutes
        LocalDateTime lastRetrainTime = LocalDateTime.now().minusMinutes(10);
        return metricRepository.findByTimestampAfter(lastRetrainTime);
    }

    private List<ExceptionLogEntity> collectNewLogs() {
        // Example logic: Fetch logs created in the last N minutes
        LocalDateTime lastRetrainTime = LocalDateTime.now().minusMinutes(10);
        return exceptionLogRepository.findByTimestampAfter(lastRetrainTime);
    }
}
