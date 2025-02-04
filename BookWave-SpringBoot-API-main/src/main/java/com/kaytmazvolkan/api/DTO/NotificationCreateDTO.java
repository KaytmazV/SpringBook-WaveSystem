package com.kaytmazvolkan.api.DTO;

/**
 * NotificationCreateDTO is a Data Transfer Object (DTO) used for creating notifications.
 * It holds the data required to create a notification, either for borrowing a book or for a fine notification.
 */
public class NotificationCreateDTO {

    /**
     * The ID of the fine associated with this notification.
     * This field is optional; it will be null if there is no fine related to the notification (e.g., for borrow notifications).
     */
    private Long fineId;  // Optional (null if no fine yet)

    /**
     * The ID of the book related to this notification.
     * This is required for both borrow and fine notifications.
     */
    private long bookId;

    /**
     * The username of the user related to this notification.
     * This is required for both borrow and fine notifications to link the notification to a specific user.
     */
    private String username;

    /**
     * Default constructor for NotificationCreateDTO.
     * This constructor is typically used when creating a new instance without any initial data.
     */
    public NotificationCreateDTO() {}

    /**
     * Constructor for Borrow Notification (when no fine is associated).
     * Sets the `fineId` to null since this is a borrow notification without a fine.
     *
     * @param bookId The ID of the book being borrowed.
     * @param username The username of the user who borrowed the book.
     */
    public NotificationCreateDTO(long bookId, String username) {
        this.fineId = null;  // Set fineId to null for borrow notifications
        this.bookId = bookId;
        this.username = username;
    }

    /**
     * Constructor for Fine Notification (when a fine is associated).
     * This constructor is used when the notification relates to a fine for a specific user.
     *
     * @param fineId The ID of the fine associated with the notification.
     * @param bookId The ID of the book for which the fine is issued.
     * @param username The username of the user who received the fine.
     */
    public NotificationCreateDTO(Long fineId, long bookId, String username) {
        this.fineId = fineId;
        this.bookId = bookId;
        this.username = username;
    }

    /**
     * Getter for the fine ID.
     *
     * @return The fine ID associated with this notification, or null if there is no fine.
     */
    public Long getFineId() {
        return fineId;
    }

    /**
     * Setter for the fine ID.
     *
     * @param fineId The fine ID to associate with this notification.
     */
    public void setFineId(Long fineId) {
        this.fineId = fineId;
    }

    /**
     * Getter for the book ID.
     *
     * @return The book ID associated with this notification.
     */
    public long getBookId() {
        return bookId;
    }

    /**
     * Setter for the book ID.
     *
     * @param bookId The book ID to associate with this notification.
     */
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    /**
     * Getter for the username.
     *
     * @return The username of the user associated with this notification.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     *
     * @param username The username to associate with this notification.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Override the toString() method to provide a string representation of this DTO.
     * Useful for logging and debugging.
     *
     * @return A string representation of the NotificationCreateDTO object.
     */
    @Override
    public String toString() {
        return "NotificationCreateDTO{" +
                "fineId=" + fineId +
                ", bookId=" + bookId +
                ", username='" + username + '\'' +
                '}';
    }
}
