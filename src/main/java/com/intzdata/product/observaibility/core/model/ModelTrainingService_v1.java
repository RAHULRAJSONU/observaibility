package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.entity.TrainingDataEntity;
import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import com.intzdata.product.observaibility.core.data.models.Metric;
import com.intzdata.product.observaibility.spi.repository.ExceptionLogRepository;
import com.intzdata.product.observaibility.spi.repository.MetricRepository;
import com.intzdata.product.observaibility.spi.repository.TrainingDataRepository;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModelTrainingService_v1 {

    private static final String MODEL_DIRECTORY = "models";

    private final ExceptionLogRepository exceptionLogRepository;
    private final MetricRepository metricRepository;
    private final TrainingDataRepository trainingDataRepository;

    public ModelTrainingService_v1(
            ExceptionLogRepository exceptionLogRepository,
            MetricRepository metricRepository,
            TrainingDataRepository trainingDataRepository) {
        this.exceptionLogRepository = exceptionLogRepository;
        this.metricRepository = metricRepository;
        this.trainingDataRepository = trainingDataRepository;
    }

    public void trainAllModels() {
        trainLogsModel();
        trainExceptionsModel();
        trainMetricsModel();
    }

    public void trainLogsModel() {
        DataSet logDataSet = prepareLogDataset();
        MultiLayerNetwork model = createModel(10, 5, 2); // Example configuration
        model.setListeners(new ScoreIterationListener(10));
        model.fit(logDataSet);
        saveModel(model, ModelConfig.LOGS_MODEL.getFileName());
    }

    public void trainExceptionsModel() {
        DataSet exceptionDataSet = prepareLogDataset();
        MultiLayerNetwork model = createModel(15, 10, 3); // Example configuration
        model.setListeners(new ScoreIterationListener(10));
        model.fit(exceptionDataSet);
        saveModel(model, ModelConfig.EXCEPTIONS_MODEL.getFileName());
    }

    public void trainMetricsModel() {
        DataSet metricsDataSet = prepareMetricsDataset();
        MultiLayerNetwork model = createModel(20, 10, 2); // Example configuration
        model.setListeners(new ScoreIterationListener(10));
        model.fit(metricsDataSet);
        saveModel(model, ModelConfig.METRICS_MODEL.getFileName());
    }

    // Creates a basic feedforward neural network model
    private MultiLayerNetwork createModel(int inputSize, int hiddenLayerSize, int outputSize) {
        MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
                .seed(123) // Random seed for reproducibility
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam(0.001)) // Optimizer with learning rate
                .weightInit(WeightInit.XAVIER) // Weight initialization
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(inputSize) // Input features
                        .nOut(hiddenLayerSize) // Hidden layer size
                        .activation(Activation.RELU) // ReLU activation for hidden layer
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MSE) // Mean Squared Error Loss
                        .nIn(hiddenLayerSize) // Number of inputs to this layer
                        .nOut(outputSize) // Number of output neurons
                        .activation(Activation.IDENTITY) // Identity activation for regression output
                        .build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(configuration);
        model.init();

        // Print model summary for validation
        System.out.println(model.summary());

        return model;
    }

    private DataSet prepareLogDataset() {
        List<ExceptionLog> logs = exceptionLogRepository.findAll(); // Fetch all exception logs

        if (logs == null || logs.isEmpty()) {
            throw new IllegalStateException("No logs found for dataset preparation.");
        }

        List<INDArray> features = new ArrayList<>();
        List<INDArray> labels = new ArrayList<>();

        for (ExceptionLog log : logs) {
            try {
                // Feature: Vectorized stack trace
                INDArray featureVector = vectorizeText(log.getStackTrace(), 200); // Max length
                features.add(featureVector);

                // Label: One-hot encoded type
                INDArray labelVector = createOneHotLabel(log.getType(), getLogTypeMap());
                labels.add(labelVector);
            } catch (Exception e) {
                System.err.println("Error processing log: " + log + ", skipping. " + e.getMessage());
            }
        }

        if (features.isEmpty() || labels.isEmpty()) {
            throw new IllegalStateException("No valid features or labels generated for the dataset.");
        }

        return new DataSet(Nd4j.vstack(features), Nd4j.vstack(labels));
    }

    // Helper method: Vectorize text
    private INDArray vectorizeText(String text, int maxLength) {
        String[] tokens = tokenizeText(text);
        INDArray vector = Nd4j.zeros(1, maxLength);

        for (int i = 0; i < Math.min(tokens.length, maxLength); i++) {
            vector.putScalar(i, Math.abs(tokens[i].hashCode() % maxLength)); // Hash-based token mapping
        }

        return vector;
    }

    // Helper method: Tokenize text
    private String[] tokenizeText(String text) {
        return text.toLowerCase().split("\\W+"); // Basic split on non-word characters
    }

    // Helper method: Create one-hot encoded label
    private INDArray createOneHotLabel(String type, Map<String, Integer> typeMap) {
        int labelIndex = typeMap.getOrDefault(type, 0); // Default to 0 if type not found
        INDArray oneHot = Nd4j.zeros(typeMap.size());
        oneHot.putScalar(labelIndex, 1);
        return oneHot;
    }

    // Helper method: Log type mapping
    private Map<String, Integer> getLogTypeMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("NPE", 0); // NullPointerException
        map.put("IOE", 1); // IOException
        map.put("OTHER", 2); // Other types
        return map;
    }

    private DataSet prepareMetricsDataset() {
        List<Metric> metrics = metricRepository.findAll(); // Fetch all metrics
        metrics.sort(Comparator.comparing(Metric::getTimestamp)); // Ensure metrics are sorted by time

        int windowSize = 10; // Sliding window size
        List<INDArray> features = new ArrayList<>();
        List<INDArray> labels = new ArrayList<>();

        for (int i = 0; i < metrics.size() - windowSize; i++) {
            List<Metric> window = metrics.subList(i, i + windowSize);
            double[] featureValues = window.stream().mapToDouble(Metric::getValue).toArray();
            INDArray featureVector = Nd4j.create(featureValues);
            features.add(featureVector);

            double nextValue = metrics.get(i + windowSize).getValue(); // Label: next metric value
            INDArray label = Nd4j.create(new double[]{nextValue});
            labels.add(label);
        }

        return new DataSet(Nd4j.vstack(features), Nd4j.vstack(labels));
    }

    private void saveTrainingData(DataSet dataset) {
        for (int i = 0; i < dataset.numExamples(); i++) {
            INDArray inputVector = dataset.getFeatures().getRow(i);
            INDArray labelVector = dataset.getLabels().getRow(i);

            TrainingDataEntity trainingData = new TrainingDataEntity();
            trainingData.setInputVector(inputVector.data().asBytes());
            trainingData.setLabelVector(labelVector.data().asBytes());

            trainingDataRepository.save(trainingData); // Save to DB
        }
    }


    // Save the trained model to disk
    private void saveModel(MultiLayerNetwork model, String fileName) {
        File directory = new File(MODEL_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File modelFile = new File(directory, fileName);
        try {
            model.save(modelFile, true);
            System.out.println("Model saved to " + modelFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save model", e);
        }
    }

    // Load a model from disk
    public MultiLayerNetwork loadModel(String fileName) {
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
