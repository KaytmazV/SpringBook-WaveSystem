package com.kaytmazvolkan.api.exception;

/**
 * Custom exception class representing a scenario where no fine is required.
 *
 * This exception extends the {@link RuntimeException} class, making it an unchecked exception.
 * It is typically thrown when a fine action or processing is attempted, but the conditions
 * for imposing a fine are not met.
 *
 * Example use case:
 * - Throw this exception when attempting to apply a fine for a user or transaction that does not
 *   meet the criteria for being fined.
 */
public class NoFineRequiredException extends RuntimeException {

    /**
     * Constructor to create an instance of NoFineRequiredException with a specific message.
     *
     * @param message The detailed message explaining the reason for the exception.
     *                This message is used to clarify why no fine should be imposed.
     */
    public NoFineRequiredException(String message) {
        super(message); // Pass the message to the superclass (RuntimeException)
    }
}