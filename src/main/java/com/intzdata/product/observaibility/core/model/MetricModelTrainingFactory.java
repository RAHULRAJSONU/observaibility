package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.data.models.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricModelTrainingFactory implements ModelTrainingFactory {
    private final MetricModelTraining metricModelTraining;

    @Autowired
    public MetricModelTrainingFactory(MetricModelTraining metricModelTraining) {
        this.metricModelTraining = metricModelTraining;
    }

    @Override
    public ModelTrainingTemplate<Metric> createModelTraining() {
        return metricModelTraining;
    }
}