package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.data.entity.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogModelTrainingFactory implements ModelTrainingFactory<LogEntity> {
    private final LogModelTraining logModelTraining;

    @Autowired
    public LogModelTrainingFactory(LogModelTraining logModelTraining) {
        this.logModelTraining = logModelTraining;
    }

    @Override
    public ModelTrainingTemplate<LogEntity> createModelTraining() {
        return logModelTraining;
    }
}