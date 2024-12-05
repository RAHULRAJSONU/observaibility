package com.intzdata.product.observaibility.spi.observers;

import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import com.intzdata.product.observaibility.core.data.models.Metric;

public interface ObservabilityEventObserver {
    void onMetricSaved(Metric metric);

    void onExceptionLogged(ExceptionLog exceptionLog);
}