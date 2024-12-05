package com.intzdata.product.observaibility.core.data.entity;

import com.intzdata.product.observaibility.core.data.models.TrainingData;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "training_data")
public class TrainingDataEntity implements TrainingData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] inputVector;

    @Lob
    private byte[] labelVector;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @Override
    public int[] getInputVector() {
        return convertBytesToIntArray(inputVector);
    }

    @Override
    public int[] getLabelVector() {
        return convertBytesToIntArray(labelVector);
    }

}