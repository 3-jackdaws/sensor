package com.mart.monitorsensors.mapper;

import com.mart.monitorsensors.data.Sensor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SensorPatchMapper {

    void copyNonNullFields(@MappingTarget Sensor target, Sensor source);
}
