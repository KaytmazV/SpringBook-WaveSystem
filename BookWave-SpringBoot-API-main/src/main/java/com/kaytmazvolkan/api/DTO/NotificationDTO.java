package com.kaytmazvolkan.api.DTO;


import com.kaytmazvolkan.api.model.Notification;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a notification related to a book transaction.
 * This class is used to transfer notification data in a simplified format.
 * It contains relevant information about the notification, including book details, reminder date, and user information.
 */
public class NotificationDTO {

    private long notification_id;              // The unique ID of the notification
    private String username;                  // The username of the user receiving the notification
    private Long book_id;                         // The ID of the book associated with the notification
    private Long fine_id;                    // The ID of the fine associated with the notification
    private LocalDateTime reminder_date;        // The date and time when the notification reminder is set
    private String message;                      // The message content of the notification

    /**
     * Default constructor required for frameworks like Jackson to deserialize the object.
     */
    public NotificationDTO() {

    }

    /**
     * Constructor that maps from a `Notification` entity to a `NotificationDTO`.
     * Extracts relevant fields from the `Notification` entity to create a simplified DTO.
     *
     * @param notification the `Notification` entity to map from
     */
    public NotificationDTO(Notification notification) {
        this.book_id = notification.getNotificationId();  // Assuming this is correct
        this.message = notification.getMessage();
        this.reminder_date = notification.getReminderDate();
        this.notification_id = notification.getNotificationId();
        this.username = notification.getUser().getUsername();

        // Safely handle null fine
        if (notification.getFine() != null) {
            this.fine_id = notification.getFine().getId();
        } else {
            this.fine_id = null;  // Set fine_id to null if no fine exists
        }
    }

    // Setter and Getters

    /**
     * Gets the ID of the book associated with this notification.
     *
     * @return the ID of the book
     */
    public Long getBook_id() {
        return book_id;
    }

    /**
     * Sets the ID of the book associated with this notification.
     *
     * @param book_id the ID of the book to set
     */
    public void setBook_id(Long book_id) {
        this.book_id = book_id;
    }

    /**
     * Gets the message content of the notification.
     *
     * @return the message of the notification
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content of the notification.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the date and time when the reminder for the notification is set.
     *
     * @return the reminder date
     */
    public LocalDateTime getReminder_date() {
        return reminder_date;
    }

    /**
     * Sets the date and time for the reminder of the notification.
     *
     * @param reminder_date the reminder date to set
     */
    public void setReminder_date(LocalDateTime reminder_date) {
        this.reminder_date = reminder_date;
    }

    /**
     * Gets the unique ID of the notification.
     *
     * @return the notification ID
     */
    public long getNotification_id() {
        return notification_id;
    }

    /**
     * Sets the unique ID of the notification.
     *
     * @param notification_id the notification ID to set
     */
    public void setNotification_id(long notification_id) {
        this.notification_id = notification_id;
    }

    /**
     * Gets the username of the user who is receiving the notification.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user who is receiving the notification.
     *
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the ID of the fine associated with the notification (if applicable).
     *
     * @return the fine ID
     */
    public Long getFine_id() {
        return fine_id;
    }

    /**
     * Sets the ID of the fine associated with the notification.
     *
     * @param fine_id the fine ID to set
     */
    public void setFine_id(Long fine_id) {
        this.fine_id = fine_id;
    }
}