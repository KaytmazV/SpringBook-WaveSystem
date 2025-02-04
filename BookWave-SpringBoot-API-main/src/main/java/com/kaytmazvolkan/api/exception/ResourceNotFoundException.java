package com.kaytmazvolkan.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to indicate that a requested resource was not found.
 * This exception is thrown when a resource is not found, and it automatically
 * triggers a `404 Not Found` HTTP status response.
 *
 * The exception extends `RuntimeException`, making it an unchecked exception.
 * It can be used in controllers to signal a resource-not-found error.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND) // This annotation causes Spring to return a 404 HTTP status when this exception is thrown
public class ResourceNotFoundException extends  RuntimeException{

    private static final long serialVersionUID = 1L; // Serial version ID for serialization

    /**
     * Constructor that takes a custom message to describe the exception.
     * The message will be used to provide more details in the response body.
     *
     * @param message The custom error message to describe the resource not found error
     */
    public ResourceNotFoundException(String message){
        // Call the superclass constructor with the message
        super(message);
    }
}
