package com.mart.monitorsensors.service;

import com.mart.monitorsensors.data.Sensor;

import java.util.List;

public interface SensorService {

    List<Sensor> getAllSensors(String name, String model);

    Sensor getSensorById(Long id);

    Sensor createSensor(Sensor sensor);

    Sensor updateSensor(Long id, Sensor sensor);

    void deleteSensor(Long id);

    Sensor patchSensor(Long id, Sensor updates);
}
