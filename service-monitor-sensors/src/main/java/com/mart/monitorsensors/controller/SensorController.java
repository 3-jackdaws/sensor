package com.mart.monitorsensors.controller;

import com.mart.api.SensorsApi;
import com.mart.dto.SensorPatchDTO;
import com.mart.dto.SensorPostDTO;
import com.mart.dto.SensorPutDTO;
import com.mart.dto.SensorResponseDTO;
import com.mart.monitorsensors.data.Sensor;
import com.mart.monitorsensors.mapper.SensorMapper;
import com.mart.monitorsensors.service.SensorService;
import com.mart.monitorsensors.validator.SensorPatchDTOValidator;
import com.mart.monitorsensors.validator.SensorPostDTOValidator;
import com.mart.monitorsensors.validator.SensorPutDTOValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class SensorController implements SensorsApi {

    private final SensorService sensorService;
    private final SensorMapper sensorMapper;
    private final SensorPostDTOValidator sensorPostDTOValidator;
    private final SensorPutDTOValidator sensorPutDTOValidator;
    private final SensorPatchDTOValidator sensorPatchDTOValidator;

    @InitBinder("sensorPostDTO")
    protected void initSensorPostBinder(WebDataBinder binder) {
        binder.addValidators(sensorPostDTOValidator);
    }

    @InitBinder("sensorPutDTO")
    protected void initSensorPutBinder(WebDataBinder binder) {
        binder.addValidators(sensorPutDTOValidator);
    }

    @InitBinder("sensorPatchDTO")
    protected void initSensorPatchBinder(WebDataBinder binder) {
        binder.addValidators(sensorPatchDTOValidator);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Override
    public ResponseEntity<SensorResponseDTO> createSensor(SensorPostDTO body) {
        log.info("Init sensor creation process: [{}]", body);
        return ResponseEntity.ok(
                sensorMapper.toResponseDTO(
                        sensorService.createSensor(
                                sensorMapper.toModel(body))));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Override
    public ResponseEntity<Void> deleteSensor(Long id) {
        log.info("Init sensor deletion process: [{}]", id);
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('VIEWER') or hasRole('ADMINISTRATOR')")
    @Override
    public ResponseEntity<SensorResponseDTO> getSensorById(Long id) {
        log.info("Get sensor by id [{}]", id);
        return ResponseEntity.ok(
                sensorMapper.toResponseDTO(
                        sensorService.getSensorById(id)));
    }

    @PreAuthorize("hasRole('VIEWER') or hasRole('ADMINISTRATOR')")
    @Override
    public ResponseEntity<List<SensorResponseDTO>> getSensors(String name, String model) {
        log.info("Getting sensors with filters - name: [{}], model: [{}]", name, model);
        List<Sensor> sensors = sensorService.getAllSensors(name, model);
        List<SensorResponseDTO> response = sensorMapper.toResponseDTOList(sensors);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Override
    public ResponseEntity<SensorResponseDTO> patchSensor(Long id, SensorPatchDTO body) {
        log.info("Init sensor patch process for id: [{}]", id);
        log.debug("Init sensor patch process: [{}]", body);
        return ResponseEntity.ok(
                sensorMapper.toResponseDTO(
                        sensorService.patchSensor(
                                id, sensorMapper.toModel(body))));
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @Override
    public ResponseEntity<SensorResponseDTO> updateSensor(Long id, SensorPutDTO body) {
        log.info("Init sensor update process for id: [{}]", id);
        log.debug("Init sensor update process: [{}]", body);
        Sensor sensor = sensorMapper.toModel(body);

        Sensor updatedSensor = sensorService.updateSensor(id, sensor);

        SensorResponseDTO responseDTO = sensorMapper.toResponseDTO(updatedSensor);

        log.info("Sensor updated successfully with id: [{}]", id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}