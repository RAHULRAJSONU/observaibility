package com.intzdata.product.observaibility.core.service;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import com.intzdata.product.observaibility.core.model.ModelTrainingService;
import com.intzdata.product.observaibility.core.model.ModelUtils;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class ObservabilityEnhancementService {

    private final ModelTrainingService modelTrainingService;
    private final MetricRepository metricRepository;
    private final ExceptionLogRepository exceptionLogRepository;

    public ObservabilityEnhancementService(ModelTrainingService modelTrainingService,
                                           MetricRepository metricRepository,
                                           ExceptionLogRepository exceptionLogRepository) {
        this.modelTrainingService = modelTrainingService;
        this.metricRepository = metricRepository;
        this.exceptionLogRepository = exceptionLogRepository;
    }

    public void trainAndEnhanceObservability() {
        log.info("Starting training for model to enhance the Observability, date: {}", new Date());
        modelTrainingService.trainAllModels();
        log.info("Observability models trained successfully.");
    }

    public boolean detectAnomaly(MetricEntity metric) {
        MultiLayerNetwork metricModel = ModelUtils.loadModel(ModelConfig.METRICS_MODEL.getFileName());
        INDArray inputVector = Nd4j.create(new double[]{metric.getValue()}); // Example input
        INDArray prediction = metricModel.output(inputVector);

        // Custom logic to classify anomalies (e.g., based on threshold)
        return prediction.getDouble(0) > 0.5; // Example: anomaly threshold
    }
}
