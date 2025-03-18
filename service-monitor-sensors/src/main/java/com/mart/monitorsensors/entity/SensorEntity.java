package com.mart.monitorsensors.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sensors")
public class SensorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 15)
    private String model;

    @Column(name = "range_from", nullable = false)
    private Integer rangeFrom;

    @Column(name = "range_to", nullable = false)
    private Integer rangeTo;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(length = 10)
    private String unit;

    @Column(length = 40)
    private String location;

    @Column(length = 200)
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
