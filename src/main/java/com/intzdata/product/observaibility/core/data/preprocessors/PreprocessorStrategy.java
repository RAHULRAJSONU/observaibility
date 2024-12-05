package com.intzdata.product.observaibility.core.data.preprocessors;

import org.nd4j.linalg.dataset.DataSet;

import java.util.List;

public interface PreprocessorStrategy<T> {
    DataSet preprocess(List<T> data);
}