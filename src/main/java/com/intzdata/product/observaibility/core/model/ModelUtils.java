package com.intzdata.product.observaibility.core.model;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;
import java.io.IOException;

public class ModelUtils {

    private static final String MODEL_DIRECTORY = System.getenv("OBSERVABILITY_MODEL_PATH");
    public static void saveModel(MultiLayerNetwork model, String fileName) {
        File modelFile = new File(MODEL_DIRECTORY, fileName);
        try {
            model.save(modelFile, true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save model", e);
        }
    }

    public static MultiLayerNetwork loadModel(String fileName) {
        File modelFile = new File(MODEL_DIRECTORY, fileName);
        if (!modelFile.exists()) {
            throw new IllegalArgumentException("Model file not found: " + fileName);
        }
        try {
            return MultiLayerNetwork.load(modelFile, true);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load model", e);
        }
    }
}
