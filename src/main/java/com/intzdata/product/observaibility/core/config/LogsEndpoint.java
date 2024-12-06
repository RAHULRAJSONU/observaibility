package com.intzdata.product.observaibility.core.config;

import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import com.intzdata.product.observaibility.core.data.entity.TrainingDataEntity;
import com.intzdata.product.observaibility.core.data.preprocessors.LogPreprocessorStrategy;
import com.intzdata.product.observaibility.core.model.LogModelTrainingFactory;
import com.intzdata.product.observaibility.core.model.ModelTrainingFactory;
import com.intzdata.product.observaibility.spi.repository.LogRepository;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Endpoint(id = "logs")
public class LogsEndpoint {
    private final ModelTrainingFactory<LogEntity> trainingFactory;
    private final LogPreprocessorStrategy logPreprocessorStrategy;
    private final TrainingDataRepository trainingDataRepository;
    private final LogRepository logRepository;

    public LogsEndpoint(
            ModelTrainingFactory<LogEntity> trainingFactory,
            LogPreprocessorStrategy logPreprocessorStrategy,
            TrainingDataRepository trainingDataRepository,
            LogModelTrainingFactory logModelTrainingFactory,
            LogRepository logRepository) {
        this.trainingFactory = logModelTrainingFactory;
        this.logPreprocessorStrategy = logPreprocessorStrategy;
        this.trainingDataRepository = trainingDataRepository;
        this.logRepository = logRepository;
    }

    @ReadOperation
    public List<TrainingDataEntity> getTrainingLogs() {
        return trainingDataRepository.findAll();
    }

    @WriteOperation
    public String addLog(@Selector String type, String stackTrace) {
//        new LogEntity(null,);
//        ModelTrainingTemplate<LogEntity> modelTraining = trainingFactory.createModelTraining();
//        modelTraining.train(List.of());
//        ExceptionLog log = new ExceptionLog(type, stackTrace);
//        INDArray processedLog = logPreprocessorStrategy.vectorizeLog(stackTrace, 200);
//        TrainingDataEntity trainingData = new TrainingDataEntity();
//        trainingData.setInputVector(processedLog.data().asBytes());
//        trainingData.setLabelVector(log.getType().getBytes());
//        trainingDataRepository.save(trainingData);
        return "Log added for training.";
    }
}
