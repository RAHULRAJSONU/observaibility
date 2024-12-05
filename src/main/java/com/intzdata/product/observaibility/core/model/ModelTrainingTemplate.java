package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.data.entity.TrainingDataEntity;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import lombok.RequiredArgsConstructor;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;

import java.util.List;

@RequiredArgsConstructor
public abstract class ModelTrainingTemplate<T> {

    private final TrainingDataRepository trainingDataRepository;

    public void train(List<T> data) {
        DataSet processedData = preprocessData(data);  // Returns a DataSet now
        MultiLayerNetwork model = createModel(processedData);
        trainModel(model, processedData);
        saveTrainingData(processedData);
        saveModel(model);
    }

    // Save training data (features and labels) to the database
    private void saveTrainingData(DataSet dataset) {
        for (int i = 0; i < dataset.numExamples(); i++) {
            INDArray inputVector = dataset.getFeatures().getRow(i);
            INDArray labelVector = dataset.getLabels().getRow(i);

            TrainingDataEntity trainingData = new TrainingDataEntity();
            trainingData.setInputVector(inputVector.data().asBytes());
            trainingData.setLabelVector(labelVector.data().asBytes());

            // Save to the database
            trainingDataRepository.save(trainingData);
        }
    }

    protected abstract DataSet preprocessData(List<T> data); // Return DataSet instead of INDArray

    protected abstract MultiLayerNetwork createModel(DataSet data); // Accept DataSet as parameter

    protected abstract void trainModel(MultiLayerNetwork model, DataSet data); // Accept DataSet as parameter

    protected abstract void saveModel(MultiLayerNetwork model);
}
