package com.mart.monitorsensors.repository;

import com.mart.monitorsensors.entity.SensorTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorTypeRepository extends JpaRepository<SensorTypeEntity, Long> {
    Optional<SensorTypeEntity> findByNameIgnoreCase(String name);
}
