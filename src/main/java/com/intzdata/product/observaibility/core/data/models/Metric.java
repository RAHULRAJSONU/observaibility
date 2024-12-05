package com.intzdata.product.observaibility.core.data.models;

import java.time.LocalDateTime;

public interface Metric {
    String getName();

    Double getValue();

    LocalDateTime getTimestamp();
}