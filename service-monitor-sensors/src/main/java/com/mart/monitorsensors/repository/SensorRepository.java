package com.mart.monitorsensors.repository;

import com.mart.monitorsensors.entity.SensorEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface SensorRepository extends JpaRepository<SensorEntity, Long>, JpaSpecificationExecutor<SensorEntity> {

    @Override
    @EntityGraph(attributePaths = {"type", "unit"})
    List<SensorEntity> findAll(Specification<SensorEntity> spec, Sort sort);
}