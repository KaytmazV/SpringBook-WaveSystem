package com.kaytmazvolkan.api.DTO;

import com.kaytmazvolkan.api.model.Fine;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a fine associated with a transaction.
 * This class is used to transfer fine data in a simplified format.
 * It contains the necessary fields that represent the fine's details.
 */
public class FineDTO {

    private long fine_id;                        // The unique ID of the fine
    private long transaction_id;                // The ID of the transaction that the fine is related to
    private double fine_amount;                // The amount of the fine
    private String fine_status;               // The status of the fine (e.g., "Paid", "Unpaid")
    private LocalDateTime paid_date;         // The date when the fine was paid, if applicable

    /**
     * Default constructor required for frameworks like Jackson to deserialize the object.
     */
    public FineDTO() {
    }

    /**
     * Constructor that maps from a `Fine` entity to a `FineDTO`.
     * Extracts relevant fields from the `Fine` object to create a simplified DTO.
     *
     * @param fine the `Fine` entity to map from
     */
    public FineDTO(Fine fine) {
        this.fine_id = fine.getId();
        this.transaction_id = fine.getTransaction().getTransactionId();
        this.fine_amount = fine.getFineAmount();
        this.fine_status = fine.getFineStatus();
        this.paid_date = fine.getPaid_date();
    }

    // Getters and setters

    /**
     * Gets the unique ID of the fine.
     *
     * @return the fine ID
     */
    public long getFine_id() {
        return fine_id;
    }

    /**
     * Sets the unique ID of the fine.
     *
     * @param fine_id the fine ID to set
     */
    public void setFine_id(long fine_id) {
        this.fine_id = fine_id;
    }

    /**
     * Gets the transaction ID associated with the fine.
     *
     * @return the transaction ID
     */
    public long getTransaction_id() {
        return transaction_id;
    }

    /**
     * Sets the transaction ID associated with the fine.
     *
     * @param transaction_id the transaction ID to set
     */
    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    /**
     * Gets the amount of the fine.
     *
     * @return the fine amount
     */
    public double getFine_amount() {
        return fine_amount;
    }

    /**
     * Sets the amount of the fine.
     *
     * @param fine_amount the fine amount to set
     */
    public void setFine_amount(double fine_amount) {
        this.fine_amount = fine_amount;
    }

    /**
     * Gets the status of the fine (e.g., "Paid" or "Unpaid").
     *
     * @return the fine status
     */
    public String getFine_status() {
        return fine_status;
    }

    /**
     * Sets the status of the fine.
     *
     * @param fine_status the fine status to set
     */
    public void setFine_status(String fine_status) {
        this.fine_status = fine_status;
    }

    /**
     * Gets the date when the fine was paid.
     *
     * @return the paid date of the fine
     */
    public LocalDateTime getPaid_date() {
        return paid_date;
    }

    /**
     * Sets the date when the fine was paid.
     *
     * @param paid_date the paid date to set
     */
    public void setPaid_date(LocalDateTime paid_date) {
        this.paid_date = paid_date;
    }

    /**
     * Returns a string representation of the FineDTO object.
     *
     * @return a string representation of the FineDTO object, including all key fields
     */
    @Override
    public String toString() {
        return "FineDTO{" +
                "fine_id=" + fine_id +
                ", transaction_id=" + transaction_id +
                ", fine_amount=" + fine_amount +
                ", fine_status='" + fine_status + '\'' +
                ", paid_date=" + (paid_date != null ? paid_date : "Not Paid") +
                '}';
    }
}
