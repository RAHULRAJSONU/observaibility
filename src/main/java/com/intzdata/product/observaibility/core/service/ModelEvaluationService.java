package com.intzdata.product.observaibility.core.service;

import com.intzdata.product.observaibility.core.config.ModelConfig;
import com.intzdata.product.observaibility.core.data.entity.ExceptionLogEntity;
import com.intzdata.product.observaibility.core.data.entity.MetricEntity;
import com.intzdata.product.observaibility.core.model.ModelUtils;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelEvaluationService {

    public void evaluateModel(
            List<MetricEntity> metrics,
            List<ExceptionLogEntity> logs,
            List<ExceptionLogEntity> exceptions) {
        MultiLayerNetwork metricModel = ModelUtils.loadModel(ModelConfig.METRICS_MODEL.getFileName());
        MultiLayerNetwork logModel = ModelUtils.loadModel(ModelConfig.LOGS_MODEL.getFileName());
        MultiLayerNetwork exceptionModel = ModelUtils.loadModel(ModelConfig.EXCEPTIONS_MODEL.getFileName());

        // Evaluate metrics model
        metrics.forEach(metric -> {
            INDArray inputVector = Nd4j.create(new double[]{metric.getValue()});
            INDArray prediction = metricModel.output(inputVector);
            System.out.printf("Metric: %s, Prediction: %.4f%n", metric.getName(), prediction.getDouble(0));
        });

        // Evaluate log model
        logs.forEach(log -> {
            INDArray inputVector = Nd4j.create(new double[]{log.getStackTrace().hashCode()}); // Example vectorization
            INDArray prediction = logModel.output(inputVector);
            System.out.printf("Log Type: %s, Prediction: %.4f%n", log.getType(), prediction.getDouble(0));
        });

        //Evaluate exception log model
        exceptions.forEach(log -> {
            INDArray inputVector = Nd4j.create(new double[]{log.getStackTrace().hashCode()}); // Example vectorization
            INDArray prediction = exceptionModel.output(inputVector);
            System.out.printf("Log Type: %s, Prediction: %.4f%n", log.getType(), prediction.getDouble(0));
        });
    }
}
