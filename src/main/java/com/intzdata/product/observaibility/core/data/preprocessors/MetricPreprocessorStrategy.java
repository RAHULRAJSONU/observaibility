package com.intzdata.product.observaibility.core.data.preprocessors;

import com.intzdata.product.observaibility.core.data.models.Metric;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class MetricPreprocessorStrategy implements PreprocessorStrategy<Metric> {

    @Override
    public DataSet preprocess(List<Metric> data) {

        if (data == null || data.isEmpty()) {
            throw new IllegalStateException("No metrics found for dataset preparation.");
        }

        data.sort(Comparator.comparing(Metric::getTimestamp));

        int windowSize = 10; // Example sliding window size
        List<INDArray> features = new ArrayList<>();
        List<INDArray> labels = new ArrayList<>();

        for (int i = 0; i < data.size() - windowSize; i++) {
            List<Metric> window = data.subList(i, i + windowSize);
            double[] featureValues = window.stream().mapToDouble(Metric::getValue).toArray();
            INDArray featureVector = Nd4j.create(featureValues);
            features.add(featureVector);

            double nextValue = data.get(i + windowSize).getValue();
            INDArray label = Nd4j.create(new double[]{nextValue});
            labels.add(label);
        }

        if (features.isEmpty() || labels.isEmpty()) {
            throw new IllegalStateException("No valid features or labels generated for the metrics dataset.");
        }

        return new DataSet(Nd4j.vstack(features), Nd4j.vstack(labels));
    }
}