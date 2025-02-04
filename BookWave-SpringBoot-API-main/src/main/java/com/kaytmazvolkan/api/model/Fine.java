package com.kaytmazvolkan.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a Fine in the database.
 */
@Entity
@Table(name = "fine")  // Table name in the database
public class Fine {

    /**
     * Unique identifier for the fine, used as the primary key in the database.
     * The id must be unique for every fine.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fine_id", nullable = false)
    private long fine_id;

    /**
     * Many-to-One relationship with the Transaction entity.
     * Each fine is associated with one transaction.
     */
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    private Transaction transaction;

    /**
     * The amount of the fine.
     * This is a monetary value that represents the fine imposed on the user.
     * This field is required and cannot be null.
     */
    @Column(name = "fine_amount", nullable = false)
    private double fineAmount;

    /**
     * The status of the fine (e.g., "Paid", "Unpaid").
     * This field represents whether the fine has been paid or not.
     * This field is required and cannot be null.
     */
    @Column(name = "fine_status", nullable = false)
    private String fineStatus;

    /**
     * The date and time when the user paid the fine.
     * This field is automatically set to the current date and time at the time of user payment.
     */
    @Column(name = "paid_date", nullable = false)
    private LocalDateTime paid_date;

    /**
     * Default constructor required by JPA.
     */
    public Fine() {
    }

    /**
     * Constructor to initialize the Fine with specific values.
     *
     * @param fineAmount  The amount of the fine.
     * @param fineStatus  The status of the fine (e.g., "Paid", "Unpaid").
     * @param transaction The Transaction of the associated with the fine.
     * @param paid_date   The user associated with the fine.
     */
    public Fine(double fineAmount, String fineStatus, Transaction transaction, LocalDateTime paid_date) {
        this.fineAmount = fineAmount;
        this.fineStatus = fineStatus;
        this.transaction = transaction;
        this.paid_date = paid_date;
    }

    // Getters and setters

    /**
     * Gets the id of the fine.
     *
     * @return The id of the fine.
     */
    public long getId() {
        return fine_id;
    }

    /**
     * Sets the id of the fine.
     *
     * @param fine_id The new id to set for the fine.
     */
    public void setId(long fine_id) {
        this.fine_id = fine_id;
    }

    /**
     * Gets the amount of the fine.
     *
     * @return The amount of the fine.
     */
    public double getFineAmount() {
        return fineAmount;
    }

    /**
     * Sets the amount of the fine.
     *
     * @param fineAmount The new amount to set for the fine.
     */
    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    /**
     * Gets the status of the fine.
     *
     * @return The status of the fine.
     */
    public String getFineStatus() {
        return fineStatus;
    }

    /**
     * Sets the status of the fine.
     *
     * @param fineStatus The new status to set for the fine.
     */
    public void setFineStatus(String fineStatus) {
        this.fineStatus = fineStatus;
    }

    /**
     * Sets the transactionID of the fine.
     *
     * @param transaction The new transaction to set for the fine.
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    // Another overloaded version of setTransaction (this can cause ambiguity)
    public void setTransactionId(Long transactionId) {
        this.transaction.setTransactionId(transactionId);
    }

    /**
     * Gets the transaction associated with this fine.
     *
     * @return The transaction associated with the fine.
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Gets the date when the payment was paid.
     *
     * @return The date associated with the fine.
     */
    public LocalDateTime getPaid_date() {
        return paid_date;
    }

    /**
     * Sets the current date when the fine is paid.
     *
     * @param paid_date The date associated with the payment fine.
     */
    public void setPaid_date(LocalDateTime paid_date) {
        this.paid_date = paid_date;
    }
}
