package com.product.manager.server.exception.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

import static com.product.manager.server.exception.api.ExceptionCode.VALIDATION_FAILED;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ProductManagerException.class)
    protected ResponseEntity<Object> handleCustomRuntimeException(final ProductManagerException exception) {
        final ApiError apiError = new ApiError();
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            apiError.setStatus(responseStatus.code());
        }

        log.error(exception.getMessage(), exception);
        apiError.setDebugMessage(exception.getMessage());
        apiError.setExceptionCode(exception.getExceptionCode().getCode());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(BAD_REQUEST);
        apiError.setDebugMessage("Validation error");
        apiError.setExceptionCode(ExceptionCode.VALIDATION_FAILED.getCode());
        apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
        apiError.addValidationError(exception.getBindingResult().getGlobalErrors());
        log.error("Validation error in handleMethodArgumentNotValid exception handler");
        log.error(exception.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(final javax.validation.ConstraintViolationException exception) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(BAD_REQUEST);
        apiError.setExceptionCode(VALIDATION_FAILED.getCode());
        apiError.setDebugMessage("Validation error");
        apiError.addValidationErrors(exception.getConstraintViolations());
        log.error("Validation error in handleConstraintViolation exception handler");
        log.error(exception.getMessage());
        return buildResponseEntity(apiError);
    }


    private ResponseEntity<Object> buildResponseEntity(final ApiError apiError) {
        apiError.setTimestamp(LocalDateTime.now());
        final HashMap<String, ApiError> errorBody = new HashMap<>();
        errorBody.put("error", apiError);
        return new ResponseEntity<>(errorBody, apiError.getStatus());
    }
}
