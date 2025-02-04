package com.kaytmazvolkan.api.model;

/**
 * Represents a request to return a borrowed book.
 * This class contains the necessary details for a user to return a book.
 */
public class ReturnRequest {
    private Long book_id;  // The ID of the book being returned
    private String username;  // The username of the user returning the book

    // Getters and Setters

    /**
     * Gets the ID of the book being returned.
     *
     * @return the ID of the book
     */
    public Long getBookId() {
        return book_id;
    }

    /**
     * Sets the ID of the book being returned.
     *
     * @param book_id the ID of the book to set
     */
    public void setBookId(Long book_id) {
        this.book_id = book_id;
    }

    /**
     * Gets the username of the user returning the book.
     *
     * @return the username of the user returning the book
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user returning the book.
     *
     * @param username the username of the user to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
