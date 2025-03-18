package com.mart.monitorsensors.data;

import lombok.Data;

@Data
public class Sensor {
    private Long id;
    private String name;
    private String model;
    private Integer rangeFrom;
    private Integer rangeTo;
    private String type;
    private String unit;
    private String location;
    private String description;
}
