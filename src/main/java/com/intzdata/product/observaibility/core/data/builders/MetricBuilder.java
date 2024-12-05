package com.intzdata.product.observaibility.core.data.builders;

import com.intzdata.product.observaibility.core.data.models.Metric;

import java.time.LocalDateTime;

public class MetricBuilder {
    private String name;
    private Double value;
    private LocalDateTime timestamp;

    public MetricBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MetricBuilder setValue(Double value) {
        this.value = value;
        return this;
    }

    public MetricBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Metric build() {
        return new Metric() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public Double getValue() {
                return value;
            }

            @Override
            public LocalDateTime getTimestamp() {
                return timestamp;
            }
        };
    }
}
