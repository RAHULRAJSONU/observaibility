package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.models.Metric;
import com.intzdata.product.observaibility.core.data.preprocessors.MetricPreprocessorStrategy;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MetricModelTraining extends ModelTrainingTemplate<Metric> {

    public MetricModelTraining(TrainingDataRepository trainingDataRepository) {
        super(trainingDataRepository);
    }

    @Override
    protected DataSet preprocessData(List<Metric> data) {
        return new MetricPreprocessorStrategy().preprocess(data); // Returns DataSet
    }

    @Override
    protected MultiLayerNetwork createModel(DataSet data) {
        return ModelFactory.createFeedForwardModel(data.getFeatures().columns(), 10, 1); // Use DataSet's feature size
    }

    @Override
    protected void trainModel(MultiLayerNetwork model, DataSet data) {
        model.fit(data); // Use DataSet directly
    }

    @Override
    protected void saveModel(MultiLayerNetwork model) {
        ModelUtils.saveModel(model, ModelConfig.METRICS_MODEL.getFileName());
    }
}
