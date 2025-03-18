package com.mart.monitorsensors.repository;

import com.mart.monitorsensors.entity.SensorUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorUnitRepository extends JpaRepository<SensorUnitEntity, Long> {
    Optional<SensorUnitEntity> findByNameIgnoreCase(String name);
}
