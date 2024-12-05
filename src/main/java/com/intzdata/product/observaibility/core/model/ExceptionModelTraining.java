package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import com.intzdata.product.observaibility.core.data.preprocessors.ExceptionPreprocessorStrategy;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExceptionModelTraining extends ModelTrainingTemplate<ExceptionLog> {

    public ExceptionModelTraining(TrainingDataRepository trainingDataRepository) {
        super(trainingDataRepository);
    }

    @Override
    protected DataSet preprocessData(List<ExceptionLog> data) {
        return new ExceptionPreprocessorStrategy().preprocess(data); // Returns DataSet
    }

    @Override
    protected MultiLayerNetwork createModel(DataSet data) {
        return ModelFactory.createFeedForwardModel(data.getFeatures().columns(), 50, 3); // Use DataSet's feature size
    }

    @Override
    protected void trainModel(MultiLayerNetwork model, DataSet data) {
        model.fit(data); // Use DataSet directly
    }

    @Override
    protected void saveModel(MultiLayerNetwork model) {
        ModelUtils.saveModel(model, ModelConfig.LOGS_MODEL.getFileName());
    }
}
