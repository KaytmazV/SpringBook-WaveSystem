package com.kaytmazvolkan.api.DTO;

/**
 * The NotificationUpdateDTO class is a Data Transfer Object (DTO) used to transfer the data
 * required to update an existing notification in the system.
 *
 * The class provides a default constructor and a parameterized constructor to initialize all fields.
 * It also includes getters and setters for each field to access and modify the data as needed.
 */
public class NotificationUpdateDTO {

    /**
     * The ID of the fine associated with the notification.
     * This field is optional. If the notification does not involve a fine, this field can be null.
     */
    private Long fineId;  // Optional (null if no fine yet)

    /**
     * The ID of the book related to this notification.
     * This field is required to identify the specific book associated with the notification.
     */
    private long bookId;

    /**
     * The username of the user who is associated with this notification.
     * This field is required for identifying the user involved in the notification.
     */
    private String username;

    /**
     * The message that describes the content or reason for the notification.
     * This field is optional and can be used to provide additional information or instructions
     * related to the notification.
     */
    private String message;

    /**
     * Default constructor for the NotificationUpdateDTO class.
     * This constructor creates an empty instance of the DTO, and it is typically used
     * when creating the object in a service method or as a placeholder.
     */
    public NotificationUpdateDTO() {}

    /**
     * Constructor for initializing the NotificationUpdateDTO with the required fields.
     * This constructor allows you to specify the fine ID, book ID, username, and message
     * at the time of object creation.
     *
     * @param fineId The ID of the fine associated with the notification (can be null if no fine).
     * @param bookId The ID of the book related to this notification.
     * @param username The username of the user associated with the notification.
     * @param message The message associated with the notification.
     */
    public NotificationUpdateDTO(Long fineId, long bookId, String username, String message) {
        this.fineId = fineId;
        this.bookId = bookId;
        this.username = username;
        this.message = message;
    }

    /**
     * Gets the fine ID associated with this notification.
     *
     * @return The fine ID, or null if no fine is associated with the notification.
     */
    public Long getFineId() {
        return fineId;
    }

    /**
     * Sets the fine ID for this notification.
     *
     * @param fineId The fine ID to associate with this notification.
     */
    public void setFineId(Long fineId) {
        this.fineId = fineId;
    }

    /**
     * Gets the book ID associated with this notification.
     *
     * @return The ID of the book related to the notification.
     */
    public long getBookId() {
        return bookId;
    }

    /**
     * Sets the book ID for this notification.
     *
     * @param bookId The ID of the book to associate with this notification.
     */
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the username of the user associated with this notification.
     *
     * @return The username of the user related to this notification.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username for this notification.
     *
     * @param username The username of the user to associate with this notification.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the message associated with this notification.
     *
     * @return The message describing the reason or content of the notification.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message for this notification.
     *
     * @param message The message to associate with this notification.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
