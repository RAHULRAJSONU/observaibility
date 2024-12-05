package com.intzdata.product.observaibility.core.data.models;

import java.time.LocalDateTime;

public interface TrainingData {
    int[] getInputVector();

    int[] getLabelVector();

    LocalDateTime getTimestamp();

    default int[] convertBytesToIntArray(byte[] bytes) {
        // Convert byte array to int array
        int[] result = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            result[i] = bytes[i] & 0xFF; // Unsigned conversion
        }
        return result;
    }
}