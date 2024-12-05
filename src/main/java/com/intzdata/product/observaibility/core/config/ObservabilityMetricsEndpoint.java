package com.intzdata.product.observaibility.core.config;

import com.intzdata.product.observaibility.core.data.entity.TrainingDataEntity;
import com.intzdata.product.observaibility.core.data.preprocessors.MetricPreprocessorStrategy;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Endpoint(id = "observabilityMetricsEndpointMetrics")
public class ObservabilityMetricsEndpoint {

    private final MetricPreprocessorStrategy metricPreprocessorStrategy;
    private final TrainingDataRepository trainingDataRepository;

    public ObservabilityMetricsEndpoint(MetricPreprocessorStrategy metricPreprocessorStrategy, TrainingDataRepository trainingDataRepository) {
        this.metricPreprocessorStrategy = metricPreprocessorStrategy;
        this.trainingDataRepository = trainingDataRepository;
    }

    @ReadOperation
    public List<TrainingDataEntity> getTrainingMetrics() {
        return trainingDataRepository.findAll();
    }

    @WriteOperation
    public String addMetric(String metricName, double value) {
//        Metric metric = new Metric(metricName, value);
//        INDArray processedMetric = metricPreprocessorStrategy.preprocess(Collections.singletonList(metric));
//        TrainingDataEntity trainingData = new TrainingDataEntity();
//        trainingData.setInputVector(processedMetric.data().asBytes());
//        trainingData.setLabelVector(metricName.getBytes());
//        trainingDataRepository.save(trainingData);
        return "Metric added for training.";
    }
}
