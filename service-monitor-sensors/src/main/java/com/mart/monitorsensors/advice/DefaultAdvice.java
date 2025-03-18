package com.mart.monitorsensors.advice;

import com.mart.dto.ApiErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class DefaultAdvice extends ResponseEntityExceptionHandler {

    private static final String PARAMETER_FORMAT_INVALID = "Parameter format is invalid: [%s]";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getMessage());
        return buildErrorResponseDto(HttpStatus.BAD_REQUEST,
                PARAMETER_FORMAT_INVALID.formatted(buildBadParamsMessage(ex)));
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity<ApiErrorDTO> handleBadRequestException(Exception e) {
        log.error(e.getMessage(), e);
        return buildErrorResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private <T> ResponseEntity<T> buildErrorResponseDto(HttpStatus status, String message) {
        var error = new ApiErrorDTO()
                .error(status.getReasonPhrase())
                .message(message);
        return ResponseEntity
                .status(status)
                .body((T) error);
    }

    private String buildBadParamsMessage(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .map(FieldError::getField)
                .collect(Collectors.joining(", "));
    }
}