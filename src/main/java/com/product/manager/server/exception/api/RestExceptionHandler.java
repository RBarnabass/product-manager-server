package com.product.manager.server.exception.api;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

import static com.product.manager.server.exception.api.ExceptionCode.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Main exception handler for all expected exceptions
     */
    @ExceptionHandler(ProductManagerException.class)
    protected ResponseEntity<Object> handleCustomRuntimeException(final ProductManagerException exception) {
        final ApiError apiError = new ApiError();
        final ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);

        if (responseStatus != null) {
            apiError.setStatus(responseStatus.code());
        }

        apiError.setDebugMessage(exception.getMessage());
        apiError.setExceptionCode(exception.getExceptionCode().getCode());
        log.error(exception.getMessage(), exception);
        return buildResponseEntity(apiError);
    }

    /**
     * Handles org.hibernate.exception.ConstraintViolationException. Happens when you try add to
     * database duplicate value or not existed value.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintFails(final ConstraintViolationException exception) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(BAD_REQUEST);
        apiError.setExceptionCode(DUPLICATE_VALUE.getCode());
        apiError.setDebugMessage("Cannot add or update row: duplicated value");
        log.error(exception.getMessage(), exception);
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails(e. g. on parameter).
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(final javax.validation.ConstraintViolationException exception) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(BAD_REQUEST);
        apiError.setExceptionCode(VALIDATION_FAILED.getCode());
        apiError.setDebugMessage("Validation error");
        apiError.addValidationErrors(exception.getConstraintViolations());
        log.error(exception.getMessage(), exception);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Validated validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(BAD_REQUEST);
        apiError.setDebugMessage("Validation error");
        apiError.setExceptionCode(VALIDATION_FAILED.getCode());
        apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
        apiError.addValidationError(exception.getBindingResult().getGlobalErrors());
        log.error(exception.getMessage(), exception);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(BAD_REQUEST);
        apiError.setDebugMessage("Malformed JSON request");
        apiError.setExceptionCode(JSON_IS_MALFORMED.getCode());
        log.error(exception.getMessage(), exception);
        return buildResponseEntity(apiError);
    }

    /**
     * Handle NoHandlerFoundException.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException exception,
                                                                   final HttpHeaders headers,
                                                                   final HttpStatus status,
                                                                   final WebRequest request) {
        final ApiError apiError = new ApiError();
        apiError.setStatus(BAD_REQUEST);
        apiError.setDebugMessage(format("Could not find the %s method for URL %s",
                exception.getHttpMethod(), exception.getRequestURL()));
        apiError.setExceptionCode(NO_EXCEPTION_HANDLER.getCode());
        log.error(exception.getMessage(), exception);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(final ApiError apiError) {
        apiError.setTimestamp(now());
        final HashMap<String, ApiError> errorBody = new HashMap<>();
        errorBody.put("error", apiError);
        return new ResponseEntity<>(errorBody, apiError.getStatus());
    }
}
