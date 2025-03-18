package com.mart.monitorsensors.service.impl;

import com.mart.monitorsensors.data.Sensor;
import com.mart.monitorsensors.entity.SensorEntity;
import com.mart.monitorsensors.entity.SensorTypeEntity;
import com.mart.monitorsensors.entity.SensorUnitEntity;
import com.mart.monitorsensors.exception.SensorNotFoundException;
import com.mart.monitorsensors.mapper.SensorMapper;
import com.mart.monitorsensors.mapper.SensorPatchMapper;
import com.mart.monitorsensors.repository.SensorRepository;
import com.mart.monitorsensors.repository.SensorTypeRepository;
import com.mart.monitorsensors.repository.SensorUnitRepository;
import com.mart.monitorsensors.service.SensorService;
import com.mart.monitorsensors.specification.SensorSpecifications;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorUnitRepository sensorUnitRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final SensorMapper sensorMapper;
    private final SensorPatchMapper patchMapper;
    private final SensorValidationService sensorValidationService;

    public List<Sensor> getAllSensors(String name, String model) {
        log.info("Searching sensors by name: [{}] and model: [{}]", name, model);

        Specification<SensorEntity> spec = Specification
                .where(SensorSpecifications.nameLike(name))
                .and(SensorSpecifications.modelLike(model));

        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        List<SensorEntity> sensorEntities = sensorRepository.findAll(spec, sort);

        return sensorMapper.toDtoList(sensorEntities);
    }

    public Sensor getSensorById(Long id) {
        log.info("Getting sensor by id: {}", id);

        return sensorRepository.findById(id)
                .map(sensorMapper::toModel)
                .orElseThrow(() -> new SensorNotFoundException("Sensor with id: [%s] not found".formatted(id)));
    }

    @Override
    public Sensor createSensor(Sensor sensor) {
        SensorTypeEntity sensorType = getSensorTypeEntity(sensor);
        SensorUnitEntity sensorUnit = getSensorUnitEntity(sensor);

        SensorEntity sensorEntity = sensorMapper.toEntity(sensor);
        sensorEntity.setType(sensorType);
        sensorEntity.setUnit(sensorUnit);

        SensorEntity savedSensorEntity = sensorRepository.save(sensorEntity);

        return sensorMapper.toModel(savedSensorEntity);
    }

    private @Nullable SensorUnitEntity getSensorUnitEntity(Sensor sensor) {
        SensorUnitEntity sensorUnit = null;
        if (sensor.getUnit() != null) {
            sensorUnit = sensorUnitRepository.findByNameIgnoreCase(sensor.getUnit().name())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Sensor unit not found: " + sensor.getUnit()));
        }
        return sensorUnit;
    }

    private SensorTypeEntity getSensorTypeEntity(Sensor sensor) {
        SensorTypeEntity sensorType = sensorTypeRepository.findByNameIgnoreCase(sensor.getType().name())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Sensor type not found: " + sensor.getType()));
        return sensorType;
    }

    @Transactional
    public Sensor updateSensor(Long id, Sensor changedSensor) {
        Sensor existingSensor = getSensorById(id);
        log.debug("Existing sensor: {}", existingSensor);
        log.info("Updating sensor with new values: {}", changedSensor);

        SensorEntity updatedSensorEntity = sensorMapper.toEntity(changedSensor);
        SensorEntity existingEntity = sensorMapper.toEntity(existingSensor);

        updatedSensorEntity.setId(existingEntity.getId());
        updatedSensorEntity.setCreatedAt(existingEntity.getCreatedAt());

        updatedSensorEntity.setUnit(getSensorUnitEntity(changedSensor));
        updatedSensorEntity.setType(getSensorTypeEntity(changedSensor));

        SensorEntity savedEntity = sensorRepository.save(updatedSensorEntity);
        log.info("Updated sensor saved: {}", savedEntity);

        return sensorMapper.toModel(savedEntity);
    }

    @Transactional
    public Sensor patchSensor(Long id, Sensor updates) {
        Sensor sensor = getSensorById(id);
        log.info("Patching sensor with id: {}", id);
        log.debug("Patching sensor: {}", sensor);
        patchMapper.copyNonNullFields(sensor, updates);

        sensorValidationService.validateSensor(sensor);

        SensorEntity sensorEntity = sensorMapper.toEntity(sensor);
        sensorEntity.setType(getSensorTypeEntity(sensor));
        sensorEntity.setUnit(getSensorUnitEntity(sensor));

        return sensorMapper.toModel(sensorRepository.save(sensorEntity));
    }

    @Transactional
    public void deleteSensor(Long id) {
        getSensorById(id);
        log.info("Deleting sensor by id: {}", id);
        sensorRepository.deleteById(id);
    }
}