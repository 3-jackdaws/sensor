package com.mart.monitorsensors.mapper;

import com.mart.dto.*;
import com.mart.monitorsensors.data.Sensor;
import com.mart.monitorsensors.entity.SensorEntity;
import com.mart.monitorsensors.entity.SensorTypeEntity;
import com.mart.monitorsensors.entity.SensorUnitEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface SensorMapper {

    @Mapping(target = "type", source = "type.name", qualifiedByName = "mapStringToSensorType")
    @Mapping(target = "unit", source = "unit.name", qualifiedByName = "mapStringToUnitOfMeasurement")
    Sensor toModel(SensorEntity sensorEntity);

    @Mapping(target = "type", source = "type", qualifiedByName = "mapSensorTypeToEntity")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "mapSensorUnitToEntity")
    @Mapping(target = "createdAt", ignore = true)
    SensorEntity toEntity(Sensor sensor);

    List<Sensor> toDtoList(List<SensorEntity> sensorEntities);

    List<SensorResponseDTO> toResponseDTOList(List<Sensor> sensor);

    @Mapping(target = "rangeFrom", source = "range.from")
    @Mapping(target = "rangeTo", source = "range.to")
    Sensor toModel(SensorPostDTO sensorDTO);

    @Mapping(target = "rangeFrom", source = "range.from")
    @Mapping(target = "rangeTo", source = "range.to")
    Sensor toModel(SensorPatchDTO sensorDTO);

    @Mapping(target = "rangeFrom", source = "range.from")
    @Mapping(target = "rangeTo", source = "range.to")
    Sensor toModel(SensorPutDTO sensorDTO);

    @Mapping(target = "range.from", source = "rangeFrom")
    @Mapping(target = "range.to", source = "rangeTo")
    SensorResponseDTO toResponseDTO(Sensor sensor);

    @Named("mapSensorTypeToEntity")
    default SensorTypeEntity mapSensorTypeToEntity(com.mart.monitorsensors.data.SensorType sensorType) {
        if (sensorType == null) return null;
        SensorTypeEntity entity = new SensorTypeEntity();
        entity.setName(sensorType.name().toLowerCase());
        return entity;
    }

    @Named("mapSensorUnitToEntity")
    default SensorUnitEntity mapSensorUnitToEntity(com.mart.monitorsensors.data.UnitOfMeasurement unit) {
        if (unit == null) return null;
        SensorUnitEntity entity = new SensorUnitEntity();
        entity.setName(unit.name().toLowerCase());
        return entity;
    }

    @Named("mapStringToSensorType")
    default com.mart.monitorsensors.data.SensorType mapStringToSensorType(String name) {
        if (name == null) return null;
        return com.mart.monitorsensors.data.SensorType.valueOf(name.toUpperCase());
    }

    @Named("mapStringToUnitOfMeasurement")
    default com.mart.monitorsensors.data.UnitOfMeasurement mapStringToUnitOfMeasurement(String name) {
        if (name == null) return null;
        return com.mart.monitorsensors.data.UnitOfMeasurement.valueOf(name.toUpperCase());
    }
}
