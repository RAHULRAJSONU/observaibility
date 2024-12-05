package com.intzdata.product.observaibility.core.data.models;

import java.time.LocalDateTime;

public interface Logs {
    String getSource();

    String getLevel();

    String getMessage();

    LocalDateTime getTimestamp();
}
