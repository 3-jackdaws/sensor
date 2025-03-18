package com.mart.monitorsensors.service.impl;

import com.mart.monitorsensors.data.Sensor;
import com.mart.monitorsensors.entity.SensorTypeEntity;
import com.mart.monitorsensors.entity.SensorUnitEntity;
import com.mart.monitorsensors.repository.SensorTypeRepository;
import com.mart.monitorsensors.repository.SensorUnitRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorReferenceService {

    private final SensorUnitRepository sensorUnitRepository;
    private final SensorTypeRepository sensorTypeRepository;

    @Cacheable(value = "sensorUnits", key = "#sensor.unit.name().toLowerCase()", unless = "#sensor.unit == null")
    public @Nullable SensorUnitEntity getSensorUnitEntity(Sensor sensor) {
        SensorUnitEntity sensorUnit = null;
        if (sensor.getUnit() != null) {
            sensorUnit = sensorUnitRepository.findByNameIgnoreCase(sensor.getUnit().name())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Sensor unit not found: " + sensor.getUnit()));
        }
        return sensorUnit;
    }

    @Cacheable(value = "sensorTypes", key = "#sensor.type.name().toLowerCase()")
    public SensorTypeEntity getSensorTypeEntity(Sensor sensor) {
        SensorTypeEntity sensorType = sensorTypeRepository.findByNameIgnoreCase(sensor.getType().name())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sensor type not found: " + sensor.getType()));
        return sensorType;
    }
}
