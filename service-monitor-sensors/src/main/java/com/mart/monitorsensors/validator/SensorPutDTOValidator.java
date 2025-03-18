package com.mart.monitorsensors.validator;

import com.mart.dto.SensorPutDTO;
import com.mart.dto.SensorType;
import com.mart.dto.UnitOfMeasurement;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SensorPutDTOValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorPutDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorPutDTO dto = (SensorPutDTO) target;

        validateName(errors, dto);
        validateModel(errors, dto);
        validateRange(errors, dto);
        validateType(errors, dto);
        validateUnit(errors, dto);
        validateLocation(errors, dto);
        validateDescription(errors, dto);
    }

    private static void validateDescription(Errors errors, SensorPutDTO dto) {
        if (dto.getDescription() != null && dto.getDescription().length() > 200) {
            errors.rejectValue("description", "description.invalid", "Description must not exceed 200 characters");
        }
    }

    private static void validateLocation(Errors errors, SensorPutDTO dto) {
        if (dto.getLocation() != null && dto.getLocation().length() > 40) {
            errors.rejectValue("location", "location.invalid", "Location must not exceed 40 characters");
        }
    }

    private static void validateUnit(Errors errors, SensorPutDTO dto) {
        if (dto.getUnit() == null) {
            errors.rejectValue("unit", "unit.required", "Unit is required");
        } else {
            try {
                UnitOfMeasurement.fromValue(dto.getUnit().toString()); // Проверяем, входит ли значение в список допустимых
            } catch (IllegalArgumentException e) {
                errors.rejectValue("unit", "unit.invalid", "Invalid Unit: " + dto.getUnit() +
                        ". Valid units are: bar, voltage, degreeCelsius, percent");
            }
        }
    }

    private static void validateType(Errors errors, SensorPutDTO dto) {
        if (dto.getType() == null) {
            errors.rejectValue("type", "type.required", "Type is required");
        } else {
            try {
                SensorType.fromValue(dto.getType().toString());
            } catch (IllegalArgumentException e) {
                errors.rejectValue("type", "type.invalid", "Invalid Type: " + dto.getType() + ". Valid types: " + List.of(SensorType.values()));
            }
        }
    }

    private static void validateRange(Errors errors, SensorPutDTO dto) {
        if (dto.getRange() != null) {
            if (dto.getRange().getFrom() == null || dto.getRange().getFrom() < 0) {
                errors.rejectValue("range.from", "range.from.invalid", "Range 'from' must be a positive integer");
            }
            if (dto.getRange().getTo() == null || dto.getRange().getTo() < 0) {
                errors.rejectValue("range.to", "range.to.invalid", "Range 'to' must be a positive integer");
            }
            if (dto.getRange().getFrom() != null && dto.getRange().getTo() != null &&
                    dto.getRange().getFrom() >= dto.getRange().getTo()) {
                errors.rejectValue("range", "range.invalid", "'From' value must be less than 'to' value");
            }
        } else {
            errors.rejectValue("range", "range.required", "Range is required");
        }
    }

    private static void validateModel(Errors errors, SensorPutDTO dto) {
        if (dto.getModel() == null || dto.getModel().isBlank()) {
            errors.rejectValue("model", "model.required", "Model is required and cannot be empty");
        } else if (dto.getModel().length() > 15) {
            errors.rejectValue("model", "model.invalid", "Model must not exceed 15 characters");
        }
    }

    private static void validateName(Errors errors, SensorPutDTO dto) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            errors.rejectValue("name", "name.required", "Name is required and cannot be empty");
        } else if (dto.getName().length() < 3 || dto.getName().length() > 30) {
            errors.rejectValue("name", "name.invalid", "Name must be between 3 and 30 characters");
        }
    }
}