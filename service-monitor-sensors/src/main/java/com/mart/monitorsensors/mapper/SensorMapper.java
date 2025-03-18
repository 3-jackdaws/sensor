package com.mart.monitorsensors.mapper;

import com.mart.dto.*;
import com.mart.monitorsensors.data.Sensor;
import com.mart.monitorsensors.entity.SensorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SensorMapper {

    Sensor toModel(SensorEntity sensorEntity);

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

    List<SensorResponseDTO> toDTOList(List<Sensor> sensors);

    @Mapping(target = "range.from", source = "rangeFrom")
    @Mapping(target = "range.to", source = "rangeTo")
    SensorResponseDTO toResponseDTO(Sensor sensor);
}
