package com.intzdata.product.observaibility.example;

import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import com.intzdata.product.observaibility.core.data.models.Metric;
import com.intzdata.product.observaibility.spi.observers.ObservabilityEventObserver;
import org.springframework.stereotype.Component;

@Component
public class LoggingObserver implements ObservabilityEventObserver {
    @Override
    public void onMetricSaved(Metric metric) {
        System.out.println("Metric saved: " + metric.getName());
    }

    @Override
    public void onExceptionLogged(ExceptionLog exceptionLog) {
        System.out.println("Exception logged: " + exceptionLog.getType());
    }
}
