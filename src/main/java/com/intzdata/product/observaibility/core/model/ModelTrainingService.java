package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import com.intzdata.product.observaibility.core.data.models.Metric;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import com.intzdata.product.observaibility.spi.repository.LogRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModelTrainingService {

    private final Map<String, ModelTrainingFactory> modelTrainingFactories;

    private final ExceptionLogRepository exceptionLogRepository;
    private final MetricRepository metricRepository;
    private final LogRepository logRepository;

    @Autowired
    public ModelTrainingService(
            ExceptionModelTrainingFactory exceptionModelTrainingFactory,
            MetricModelTrainingFactory metricModelTrainingFactory,
            LogModelTrainingFactory logModelTrainingFactory,
            ExceptionLogRepository exceptionLogRepository,
            MetricRepository metricRepository,
            LogRepository logRepository) {

        this.modelTrainingFactories = Map.of(
                ModelConfig.EXCEPTIONS_MODEL.getName(), exceptionModelTrainingFactory,
                ModelConfig.METRICS_MODEL.getName(), metricModelTrainingFactory,
                ModelConfig.LOGS_MODEL.getName(), logModelTrainingFactory
        );
        this.exceptionLogRepository = exceptionLogRepository;
        this.metricRepository = metricRepository;
        this.logRepository = logRepository;
    }

    // Train all models (logs, metrics, exceptions)
    public void trainAllModels() {
        trainModel(ModelConfig.LOGS_MODEL.getName());
        trainModel(ModelConfig.METRICS_MODEL.getName());
        trainModel(ModelConfig.EXCEPTIONS_MODEL.getName());
    }

    // Train a specific model based on its type
    public void trainModel(String modelType) {
        ModelTrainingFactory factory = modelTrainingFactories.get(modelType);
        if (factory != null) {
            ModelTrainingTemplate modelTrainingTemplate = factory.createModelTraining();

            if (modelType.equals(ModelConfig.LOGS_MODEL.getName())) {
                List<LogEntity> logs = logRepository.findAll();
                modelTrainingTemplate.train(logs);
            } else if (modelType.equals(ModelConfig.METRICS_MODEL.getName())) {
                List<Metric> metrics = metricRepository.findAll();
                modelTrainingTemplate.train(metrics);
            } else if (modelType.equals(ModelConfig.EXCEPTIONS_MODEL.getName())) {
                List<ExceptionLog> exceptions = exceptionLogRepository.findAll();
                modelTrainingTemplate.train(exceptions);
            }
        } else {
            throw new IllegalArgumentException("Unknown model type: " + modelType);
        }
    }

}
