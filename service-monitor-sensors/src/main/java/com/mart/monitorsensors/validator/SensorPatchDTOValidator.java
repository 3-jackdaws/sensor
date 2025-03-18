package com.mart.monitorsensors.validator;

import com.mart.dto.SensorPatchDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SensorPatchDTOValidator implements Validator {

    private static final List<String> VALID_TYPES = List.of("Pressure", "Voltage", "Temperature", "Humidity");
    private static final List<String> VALID_UNITS = List.of("bar", "voltage", "°С", "%");

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorPatchDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorPatchDTO dto = (SensorPatchDTO) target;

        if (dto.getName() != null) {
            validateName(errors, dto.getName());
        }
        if (dto.getModel() != null) {
            validateModel(errors, dto.getModel());
        }
        if (dto.getRange() != null) {
            validateRange(errors, dto.getRange().getFrom(), dto.getRange().getTo());
        }
        if (dto.getType() != null) {
            validateType(errors, dto.getType().toString());
        }
        if (dto.getUnit() != null) {
            validateUnit(errors, dto.getUnit().toString());
        }
        if (dto.getLocation() != null) {
            validateLocation(errors, dto.getLocation());
        }
        if (dto.getDescription() != null) {
            validateDescription(errors, dto.getDescription());

        }
    }

    private void validateName(Errors errors, String name) {
        if (name.isBlank()) {
            errors.rejectValue("name", "name.required", "Name cannot be empty");
        } else if (name.length() < 3 || name.length() > 30) {
            errors.rejectValue("name", "name.invalid", "Name must be between 3 and 30 characters");
        }
    }

    private void validateModel(Errors errors, String model) {
        if (model.isBlank()) {
            errors.rejectValue("model", "model.required", "Model cannot be empty");
        } else if (model.length() > 15) {
            errors.rejectValue("model", "model.invalid", "Model must not exceed 15 characters");
        }
    }

    private void validateRange(Errors errors, Integer from, Integer to) {
        if (from != null && from < 0) {
            errors.rejectValue("range.from", "range.from.invalid", "Range 'from' must be a positive integer");
        }
        if (to != null && to < 0) {
            errors.rejectValue("range.to", "range.to.invalid", "Range 'to' must be a positive integer");
        }
        if (from != null && to != null && from >= to) {
            errors.rejectValue("range", "range.invalid", "'From' value must be less than 'to' value");
        }
    }

    private void validateType(Errors errors, String type) {
        if (!VALID_TYPES.contains(type)) {
            errors.rejectValue("type", "type.invalid", "Type must be one of: " + VALID_TYPES);
        }
    }

    private void validateUnit(Errors errors, String unit) {
        if (!VALID_UNITS.contains(unit)) {
            errors.rejectValue("unit", "unit.invalid", "Unit must be one of: " + VALID_UNITS);
        }
    }

    private void validateLocation(Errors errors, String location) {
        if (location.length() > 40) {
            errors.rejectValue("location", "location.invalid", "Location must not exceed 40 characters");
        }
    }

    private void validateDescription(Errors errors, String description) {
        if (description.length() > 200) {
            errors.rejectValue("description", "description.invalid", "Description must not exceed 200 characters");
        }
    }
}