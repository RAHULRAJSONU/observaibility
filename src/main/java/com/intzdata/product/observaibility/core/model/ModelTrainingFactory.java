package com.intzdata.product.observaibility.core.model;

public interface ModelTrainingFactory<T> {
    ModelTrainingTemplate<T> createModelTraining();
}