package com.intzdata.product.observaibility.core.data.preprocessors;

import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import lombok.extern.slf4j.Slf4j;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class LogPreprocessorStrategy implements PreprocessorStrategy<LogEntity> {

    @Override
    public DataSet preprocess(List<LogEntity> data) {
        log.info("preprocessing Log Strategy to prepare the dataset, data: {}",data);
        if (data == null || data.isEmpty()) {
            throw new IllegalStateException("No logs found for dataset preparation.");
        }
        List<INDArray> features = new ArrayList<>();
        List<INDArray> labels = new ArrayList<>();

        for (LogEntity logData : data) {
            try {
                INDArray featureVector = vectorizeLog(logData.getMessage(), 200); // Example max length
                features.add(featureVector);

                INDArray labelVector = createOneHotLabel(logData.getLevel(), getLogLevelMap());
                labels.add(labelVector);
            }catch (Exception e){
                log.error("Error processing log: {}, skipping. {}", log, e.getMessage());
            }
        }

        return new DataSet(Nd4j.vstack(features), Nd4j.vstack(labels));
    }

    private INDArray vectorizeLog(String message, int maxLength) {
        String[] tokens = tokenizeText(message);
        INDArray vector = Nd4j.zeros(1, maxLength);

        for (int i = 0; i < Math.min(tokens.length, maxLength); i++) {
            vector.putScalar(i, Math.abs(tokens[i].hashCode() % maxLength)); // Hash-based token mapping
        }

        return vector;
    }

    private String[] tokenizeText(String text) {
        return text.toLowerCase().split("\\W+"); // Basic split on non-word characters
    }

    private INDArray createOneHotLabel(String level, Map<String, Integer> levelMap) {
        int labelIndex = levelMap.getOrDefault(level, 0);
        INDArray oneHot = Nd4j.zeros(levelMap.size());
        oneHot.putScalar(labelIndex, 1);
        return oneHot;
    }

    private Map<String, Integer> getLogLevelMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("INFO", 0);
        map.put("WARN", 1);
        map.put("ERROR", 2);
        return map;
    }
}
