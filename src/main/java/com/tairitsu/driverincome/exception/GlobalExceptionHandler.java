package com.tairitsu.driverincome.exception;

import com.tairitsu.driverincome.exception.custom.ResourceNotFoundException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for REST APIs.
 * <p>This class centralizes exception handling and converts
 * application exceptions into standardized HTTP responses.
 * <p>Handled exception categories include:
 * <ul>
 *     <li>Validation errors</li>
 *     <li>Resource not found errors</li>
 *     <li>Malformed request bodies</li>
 *     <li>Illegal arguments</li>
 * </ul>
 */
@SuppressWarnings("unused")
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles request body validation errors triggered by @Valid.
     * <p>Returns field-specific validation messages.
     * @param ex validation exception
     * @return HTTP 400 response containing validation details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(err -> errors.put(
                        err.getField(),
                        err.getDefaultMessage()
                ));
        Map<String, Object> response = new HashMap<>();
        response.put("code", "VALIDATION_ERROR");
        response.put("message", "Invalid request");
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }
    /**
     * Handles validation errors for method parameters such as
     * @RequestParam and @PathVariable.
     * @param ex validation exception
     * @return HTTP 400 response containing validation messages
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", "VALIDATION_ERROR");
        response.put("message", "Invalid request");
        List<String> errors = ex.getAllErrors()
                .stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();
        response.put("errors", errors);
        return ResponseEntity.badRequest().body(response);
    }
    /**
     * Handles resource-not-found exceptions.
     * @param ex resource not found exception
     * @return HTTP 404 response
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "message", ex.getMessage(),
                        "status", 404
                ));
    }
    /**
     * Handles invalid enum values in request bodies,
     * such as unsupported trip or expense types.
     * @param ex request parsing exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleInvalidEnum(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "code", "INVALID_REQUEST_BODY",
                        "message", "Invalid request body"
                )
        );
    }
    /**
     * Handles illegal argument exceptions thrown by business logic.
     * @param ex illegal argument exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                Map.of(
                        "code", "INVALID_ARGUMENT",
                        "message", ex.getMessage()
                )
        );
    }
}
