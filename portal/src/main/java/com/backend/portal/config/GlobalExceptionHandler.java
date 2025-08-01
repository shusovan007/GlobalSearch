package com.backend.portal.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        logger.error("Unhandled exception: ", ex);
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, "Resource not found", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, "Invalid input", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.add(err.getField() + ": " + err.getDefaultMessage()));
        return errorResponse(HttpStatus.BAD_REQUEST, "Validation failed", errors.toString());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return errorResponse(HttpStatus.NOT_FOUND, "User Not Found", ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> errorResponse(HttpStatus status, String error, String details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errorMessage", error);
        body.put("rejectionReason", details);
        return new ResponseEntity<>(body, status);
    }
}
