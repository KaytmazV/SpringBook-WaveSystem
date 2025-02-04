package com.kaytmazvolkan.api.exception;

/**
 * Custom exception class representing an error scenario where a fine or payment
 * has already been paid or settled, and therefore cannot be processed again.
 *
 * This exception extends the {@link RuntimeException} class, making it an unchecked exception.
 * It is typically thrown when an operation attempts to apply a payment or fine action to an already paid fine.
 *
 * Example use case:
 * - Throw this exception when trying to charge a user a fine that has already been marked as paid.
 */
public class AlreadyPaidException extends RuntimeException {

    /**
     * Constructor to create an instance of AlreadyPaidException with a specific message.
     *
     * @param message The detailed message that explains the reason for the exception.
     *                This message can be used to provide more context for why the exception is thrown.
     */
    public AlreadyPaidException(String message) {
        super(message); // Pass the message to the superclass (RuntimeException)
    }
}
