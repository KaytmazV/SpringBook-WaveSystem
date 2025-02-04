package com.kaytmazvolkan.api.DTO;

/**
 * FineCreateRequestDTO is a Data Transfer Object (DTO) used for creating fines.
 * It contains the details needed to create a fine, specifically the transaction ID
 * associated with the fine creation request.
 *
 * This class is used in the API request to pass data from the client to the server.
 */
public class FineCreateRequestDTO {

    /**
     * The transaction ID associated with the fine.
     * This ID is used to identify the transaction for which the fine needs to be created.
     *
     * Example: If the user is overdue in returning a book, this ID would be used
     * to associate the fine with that specific transaction.
     */
    private long transaction_id;

    /**
     * Getter for the transaction ID.
     *
     * @return The transaction ID of the fine creation request.
     */
    public long getTransactionId() {
        return transaction_id;
    }

    /**
     * Setter for the transaction ID.
     *
     * @param transactionId The transaction ID to be set.
     */
    public void setTransactionId(long transactionId) {
        this.transaction_id = transactionId;
    }

    /**
     * Default constructor for FineCreateRequestDTO.
     * It is generally used when creating a new instance of this DTO.
     */
    public FineCreateRequestDTO() {
        // Default constructor
    }

    /**
     * Overloaded constructor for FineCreateRequestDTO.
     * Allows creation of an instance with the specified transaction ID.
     *
     * @param transactionId The transaction ID associated with the fine creation.
     */
    public FineCreateRequestDTO(long transactionId) {
        this.transaction_id = transactionId;
    }

    /**
     * Override the toString() method to provide a string representation of this DTO.
     * It helps in debugging and logging to know the contents of the object.
     *
     * @return A string representation of the FineCreateRequestDTO object.
     */
    @Override
    public String toString() {
        return "FineCreateRequestDTO{" +
                "transaction_id=" + transaction_id +
                '}';
    }
}
