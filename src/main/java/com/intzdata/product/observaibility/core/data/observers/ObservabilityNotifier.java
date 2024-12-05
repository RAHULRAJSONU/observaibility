package com.intzdata.product.observaibility.core.data.observers;

import com.intzdata.product.observaibility.core.data.models.ExceptionLog;
import com.intzdata.product.observaibility.core.data.models.Metric;
import com.intzdata.product.observaibility.spi.observers.ObservabilityEventObserver;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ObservabilityNotifier {
    private final CopyOnWriteArraySet<ObservabilityEventObserver> observers = new CopyOnWriteArraySet<>();

    public void addObserver(ObservabilityEventObserver observer) {
        observers.add(observer);
    }

    public void notifyMetricSaved(Metric metric) {
        observers.forEach(observer -> observer.onMetricSaved(metric));
    }

    public void notifyExceptionLogged(ExceptionLog exceptionLog) {
        observers.forEach(observer -> observer.onExceptionLogged(exceptionLog));
    }
}
