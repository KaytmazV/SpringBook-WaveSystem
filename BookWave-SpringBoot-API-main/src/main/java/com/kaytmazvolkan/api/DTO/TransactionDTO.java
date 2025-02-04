package com.kaytmazvolkan.api.DTO;

import com.kaytmazvolkan.api.model.Transaction;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing a transaction related to a book.
 * This class is used to transfer transaction data in a simplified format.
 * It contains relevant information about the transaction such as user details, book details, and transaction dates.
 */
public class TransactionDTO {

    private long transaction_id;                // The unique ID of the transaction
    private String username;                   // The username of the user involved in the transaction
    private long book_id;                     // The ID of the book involved in the transaction
    private LocalDate issue_date;            // The date the book was issued
    private LocalDate due_date;             // The due date for returning the book
    private LocalDate return_date;         // The actual date the book was returned (may be null if not yet returned)

    /**
     * Default constructor required for frameworks like Jackson to deserialize the object.
     */
    public TransactionDTO() {
    }

    /**
     * Constructor that maps from a `Transaction` entity to a `TransactionDTO`.
     * Extracts relevant fields from the `Transaction` object to create a simplified DTO.
     *
     * @param transaction the `Transaction` entity to map from
     */
    public TransactionDTO(Transaction transaction) {
        this.transaction_id = transaction.getTransactionId();
        this.username = transaction.getUser().getUsername();
        this.book_id = transaction.getBook().getBook_id();
        this.issue_date = transaction.getIssueDate();
        this.due_date = transaction.getDueDate();
        this.return_date = transaction.getReturnDate();
    }

    // Getters and setters

    /**
     * Gets the unique ID of the transaction.
     *
     * @return the transaction ID
     */
    public long getTransaction_id() {
        return transaction_id;
    }

    /**
     * Sets the unique ID of the transaction.
     *
     * @param transaction_id the transaction ID to set
     */
    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    /**
     * Gets the username of the user involved in the transaction.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user involved in the transaction.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the ID of the book involved in the transaction.
     *
     * @return the book ID
     */
    public long getBook_id() {
        return book_id;
    }

    /**
     * Sets the ID of the book involved in the transaction.
     *
     * @param book_id the book ID to set
     */
    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    /**
     * Gets the date when the book was issued.
     *
     * @return the issue date
     */
    public LocalDate getIssue_date() {
        return issue_date;
    }

    /**
     * Sets the date when the book was issued.
     *
     * @param issue_date the issue date to set
     */
    public void setIssue_date(LocalDate issue_date) {
        this.issue_date = issue_date;
    }

    /**
     * Gets the due date by which the book should be returned.
     *
     * @return the due date
     */
    public LocalDate getDue_date() {
        return due_date;
    }

    /**
     * Sets the due date by which the book should be returned.
     *
     * @param due_date the due date to set
     */
    public void setDue_date(LocalDate due_date) {
        this.due_date = due_date;
    }

    /**
     * Gets the actual date the book was returned.
     * This could be `null` if the book has not been returned yet.
     *
     * @return the return date (or null if not returned)
     */
    public LocalDate getReturn_date() {
        return return_date;
    }

    /**
     * Sets the actual date the book was returned.
     *
     * @param return_date the return date to set
     */
    public void setReturn_date(LocalDate return_date) {
        this.return_date = return_date;
    }
}
