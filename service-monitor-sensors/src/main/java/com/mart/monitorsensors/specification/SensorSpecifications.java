package com.mart.monitorsensors.specification;

import com.mart.monitorsensors.entity.SensorEntity;
import org.springframework.data.jpa.domain.Specification;

public class SensorSpecifications {

    private static final String NAME = "name";
    private static final String MODEL = "model";

    public static Specification<SensorEntity> nameLike(String name) {
        return (root, query, builder) ->
                name == null ? null : builder.like(root.get(NAME), "%" + name + "%");
    }

    public static Specification<SensorEntity> modelLike(String model) {
        return (root, query, builder) ->
                model == null ? null : builder.like(root.get(MODEL), "%" + model + "%");
    }
}
