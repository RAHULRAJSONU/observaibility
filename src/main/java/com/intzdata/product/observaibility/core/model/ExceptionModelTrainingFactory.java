package com.intzdata.product.observaibility.core.model;

import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionModelTrainingFactory implements ModelTrainingFactory {
    private final ExceptionModelTraining exceptionModelTraining;

    @Autowired
    public ExceptionModelTrainingFactory(ExceptionModelTraining exceptionModelTraining) {
        this.exceptionModelTraining = exceptionModelTraining;
    }

    @Override
    public ModelTrainingTemplate<ExceptionLog> createModelTraining() {
        return exceptionModelTraining;
    }
}