package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import com.intzdata.product.observaibility.core.data.preprocessors.LogPreprocessorStrategy;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogModelTraining extends ModelTrainingTemplate<LogEntity> {

    @Autowired
    private LogPreprocessorStrategy preprocessor;

    public LogModelTraining(TrainingDataRepository trainingDataRepository) {
        super(trainingDataRepository);
    }

    @Override
    protected DataSet preprocessData(List<LogEntity> data) {
        return preprocessor.preprocess(data);
    }

    @Override
    protected MultiLayerNetwork createModel(DataSet data) {
        return ModelFactory.createFeedForwardModel(data.getFeatures().columns(), 50, 3); // Example configuration
    }

    @Override
    protected void trainModel(MultiLayerNetwork model, DataSet data) {
        model.fit(data);
    }

    @Override
    protected void saveModel(MultiLayerNetwork model) {
        ModelUtils.saveModel(model, ModelConfig.LOGS_MODEL.getFileName());
    }
}
