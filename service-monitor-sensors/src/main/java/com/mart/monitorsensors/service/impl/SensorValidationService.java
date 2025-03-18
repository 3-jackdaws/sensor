package com.mart.monitorsensors.service.impl;

import com.mart.monitorsensors.data.Sensor;
import org.springframework.stereotype.Service;

@Service
public class SensorValidationService {

    public void validateSensor(Sensor sensor) {
        validateRange(sensor);
    }

    private void validateRange(Sensor sensor) {
        Integer rangeFrom = sensor.getRangeFrom();
        Integer rangeTo = sensor.getRangeTo();

        if (rangeFrom != null && rangeTo != null && rangeFrom >= rangeTo) {
            throw new IllegalArgumentException("RangeFrom must be less than RangeTo. Current values: RangeFrom = "
                    + rangeFrom + ", RangeTo = " + rangeTo);
        }
    }
}