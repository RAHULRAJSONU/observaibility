package com.intzdata.product.observaibility.core.data.entity;

import com.intzdata.product.observaibility.core.data.models.Logs;
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
@Table(name = "logs")
public class LogEntity implements Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String source; // The source of the log (e.g., application name, system module)

    @Column(nullable = false)
    private String level; // Log level (e.g., INFO, WARN, ERROR)

    @Lob
    @Column(nullable = false)
    private String message; // Log message

    @Column(nullable = false)
    private LocalDateTime timestamp; // When the log was generated

}
