package com.intzdata.product.observaibility.core.data.builders;

import com.intzdata.product.observaibility.core.data.models.ExceptionLog;

import java.time.LocalDateTime;

public class ExceptionLogBuilder {
    private String type;
    private String message;
    private String stackTrace;
    private LocalDateTime timestamp;

    public ExceptionLogBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public ExceptionLogBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public ExceptionLogBuilder setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    public ExceptionLogBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ExceptionLog build() {
        return new ExceptionLog() {
            @Override
            public String getType() {
                return type;
            }

            @Override
            public String getMessage() {
                return message;
            }

            @Override
            public String getStackTrace() {
                return stackTrace;
            }

            @Override
            public LocalDateTime getTimestamp() {
                return timestamp;
            }
        };
    }
}
