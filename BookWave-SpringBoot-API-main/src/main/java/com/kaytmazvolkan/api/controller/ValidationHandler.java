package com.kaytmazvolkan.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for handling validation errors.
 * This class is responsible for catching validation exceptions that occur
 * when the request body does not meet the expected constraints.
 * It customizes the response sent back to the client when validation fails.
 *
 * It extends `ResponseEntityExceptionHandler` to handle Spring's
 * `MethodArgumentNotValidException`, which is thrown when validation fails
 * on request body arguments (e.g., `@Valid` annotations on `@RequestBody` parameters).
 */
@ControllerAdvice // This annotation indicates that this class will handle exceptions globally
public class ValidationHandler extends ResponseEntityExceptionHandler{

    /**
     * Handles MethodArgumentNotValidException, which occurs when validation fails on
     * method arguments (e.g., when `@Valid` fails to validate input parameters).
     *
     * @param ex the exception that contains the validation errors
     * @param headers the HTTP headers
     * @param status the HTTP status code (BAD_REQUEST)
     * @param request the web request that caused the exception
     * @return a ResponseEntity with a map of validation errors and HTTP status 400 (BAD_REQUEST)
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        // Create a map to hold field error messages
        Map<String, String> errors = new HashMap<>();

        // Iterate through the validation errors and extract the field name and error message
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField(); // Get the field name where the error occurred
            String message = error.getDefaultMessage();    // Get the error message for the field
            errors.put(fieldName, message);   // Add the error to the map
        });

        // Return the error details in the response body with BAD_REQUEST (400) status
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}