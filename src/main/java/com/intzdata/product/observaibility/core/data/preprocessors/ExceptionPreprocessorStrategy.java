package com.intzdata.product.observaibility.core.data.preprocessors;

import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExceptionPreprocessorStrategy implements PreprocessorStrategy<ExceptionLog> {

    @Override
    public DataSet preprocess(List<ExceptionLog> data) {
        List<INDArray> features = new ArrayList<>();
        List<INDArray> labels = new ArrayList<>();

        for (ExceptionLog log : data) {
            INDArray featureVector = vectorizeText(log.getStackTrace(), 200); // Example max length
            features.add(featureVector);

            INDArray labelVector = createOneHotLabel(log.getType(), getLogTypeMap());
            labels.add(labelVector);
        }

        return new DataSet(Nd4j.vstack(features), Nd4j.vstack(labels));
    }

    private INDArray vectorizeText(String text, int maxLength) {
        String[] tokens = text.toLowerCase().split("\\W+");
        INDArray vector = Nd4j.zeros(1, maxLength);

        for (int i = 0; i < Math.min(tokens.length, maxLength); i++) {
            vector.putScalar(i, Math.abs(tokens[i].hashCode() % maxLength));
        }
        return vector;
    }

    private INDArray createOneHotLabel(String type, Map<String, Integer> typeMap) {
        int labelIndex = typeMap.getOrDefault(type, 0);
        INDArray oneHot = Nd4j.zeros(typeMap.size());
        oneHot.putScalar(labelIndex, 1);
        return oneHot;
    }

    private Map<String, Integer> getLogTypeMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("NPE", 0);
        map.put("IOE", 1);
        map.put("OTHER", 2);
        return map;
    }
}