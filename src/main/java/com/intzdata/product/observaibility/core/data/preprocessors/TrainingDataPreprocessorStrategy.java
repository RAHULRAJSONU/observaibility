package com.intzdata.product.observaibility.core.data.preprocessors;

import com.intzdata.product.observaibility.core.data.models.TrainingData;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainingDataPreprocessorStrategy implements PreprocessorStrategy<TrainingData> {

    @Override
    public DataSet preprocess(List<TrainingData> data) {
        List<INDArray> features = new ArrayList<>();
        List<INDArray> labels = new ArrayList<>();

        for (TrainingData trainingData : data) {
            // Convert int[] to float[] and create INDArray
            float[] inputVector = convertToFloatArray(trainingData.getInputVector());
            float[] labelVector = convertToFloatArray(trainingData.getLabelVector());

            features.add(Nd4j.create(inputVector));
            labels.add(Nd4j.create(labelVector));
        }

        return new DataSet(Nd4j.vstack(features), Nd4j.vstack(labels));
    }

    private float[] convertToFloatArray(int[] intArray) {
        float[] floatArray = new float[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            floatArray[i] = intArray[i];
        }
        return floatArray;
    }
}