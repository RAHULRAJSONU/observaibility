package com.intzdata.product.observaibility.core.data.builders;

import com.intzdata.product.observaibility.core.data.models.TrainingData;

import java.time.LocalDateTime;

public class TrainingDataBuilder {
    private byte[] inputVector;
    private byte[] labelVector;
    private LocalDateTime timestamp;

    public TrainingDataBuilder setInputVector(byte[] inputVector) {
        this.inputVector = inputVector;
        return this;
    }

    public TrainingDataBuilder setLabelVector(byte[] labelVector) {
        this.labelVector = labelVector;
        return this;
    }

    public TrainingDataBuilder setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TrainingData build() {
        return new TrainingData() {
            @Override
            public int[] getInputVector() {
                return convertBytesToIntArray(inputVector);
            }

            @Override
            public int[] getLabelVector() {
                return convertBytesToIntArray(labelVector);
            }

            @Override
            public LocalDateTime getTimestamp() {
                return timestamp;
            }
        };
    }
}
