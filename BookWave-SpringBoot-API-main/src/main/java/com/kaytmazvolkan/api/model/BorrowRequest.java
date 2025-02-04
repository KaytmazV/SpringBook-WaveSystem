package com.kaytmazvolkan.api.model;

/**
 * Represents a borrow request for a book in the system.
 * This class holds the necessary details for a user to borrow a book.
 */
public class BorrowRequest {

    private Long book_id;  // The ID of the book being borrowed
    private String username;  // The ID of the user borrowing the book

    // Getters and Setters

    /**
     * Gets the ID of the book being borrowed.
     *
     * @return the ID of the book
     */
    public Long getBookId() {
        return book_id;
    }

    /**
     * Sets the ID of the book being borrowed.
     *
     * @param book_id the ID of the book to set
     */
    public void setBookId(Long book_id) {
        this.book_id = book_id;
    }

    /**
     * Gets the username of the user borrowing the book.
     *
     * @return the username of the borrower
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user borrowing the book.
     *
     * @param username the username of the borrower to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
