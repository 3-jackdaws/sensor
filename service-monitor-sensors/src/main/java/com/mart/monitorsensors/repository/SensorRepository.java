package com.mart.monitorsensors.repository;

import com.mart.monitorsensors.entity.SensorEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface SensorRepository extends JpaRepository<SensorEntity, Long>, JpaSpecificationExecutor<SensorEntity> {

    @Override
    @EntityGraph(attributePaths = {"type", "unit"})
    @NotNull
    List<SensorEntity> findAll(Specification<SensorEntity> spec, @NotNull Sort sort);
}