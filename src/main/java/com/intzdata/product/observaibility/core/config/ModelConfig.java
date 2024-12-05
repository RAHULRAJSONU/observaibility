package com.intzdata.product.observaibility.core.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ModelConfig {
    LOGS_MODEL("logs", "logsModel.zip"),
    EXCEPTIONS_MODEL("exceptions", "exceptionsModel.zip"),
    METRICS_MODEL("metrics", "metricsModel.zip");
    private final String name;
    private final String fileName;
}
