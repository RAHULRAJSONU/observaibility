package com.intzdata.product.observaibility.core.data.models;

import java.time.LocalDateTime;

public interface ExceptionLog {
    String getType();

    String getMessage();

    String getStackTrace();

    LocalDateTime getTimestamp();
}
