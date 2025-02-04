package com.kaytmazvolkan.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * Entity representing a Notification in the database.
 */
@Entity
@Table(name = "notification")
public class Notification {

    /**
     * Unique identifier for the notification.
     * This will be automatically generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", nullable = false)
    private Long notificationId; // Primary Key

    /**
     * Many-to-One relationship with the Fine entity.
     * Each notification is associated with one fine.
     */
    @ManyToOne
    @JoinColumn(name = "fine_id", nullable = false) // Foreign Key to Fine
    private Fine fine; // Fine associated with the notification

    /**
     * Many-to-One relationship with the User entity.
     * Each notification is associated with a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "username", nullable = false) // Foreign Key to User (username)
    private User user; // User who is associated with the notification

    /**
     * Many-to-One relationship with the Book entity.
     * Each notification is linked to a specific book.
     */
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false) // Foreign Key to Book
    private Book book; // Book associated with the notification

    /**
     * The date and time when the reminder for the notification should occur.
     */
    @Column(name = "reminder_date", nullable = false)
    private LocalDateTime reminderDate;

    /**
     * The message that will be displayed in the notification.
     * It can contain details like fine amount, book due date, etc.
     */
    @Column(name = "message", nullable = false)
    private String message;

    /**
     * Default constructor.
     * This is required by JPA for entity instantiation.
     */
    public Notification() {}

    /**
     * Parameterized constructor to create a new notification with all fields.
     *
     * @param notificationId Unique identifier for the notification.
     * @param reminderDate The date and time for the reminder.
     * @param user The user associated with the notification.
     * @param book The book associated with the notification.
     * @param fine The fine associated with the notification.
     * @param message The message of the notification.
     */
    public Notification(Long notificationId, LocalDateTime reminderDate, User user, Book book, Fine fine, String message) {
        this.notificationId = notificationId;
        this.reminderDate = reminderDate;
        this.user = user;
        this.book = book;
        this.fine = fine;
        this.message = message;
    }

    // Getters and setters

    /**
     * Gets the unique identifier for this notification.
     *
     * @return The notification ID.
     */
    public Long getNotificationId() {
        return notificationId;
    }

    /**
     * Sets the unique identifier for this notification.
     *
     * @param notificationId The notification ID.
     */
    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Gets the fine associated with this notification.
     *
     * @return The Fine entity.
     */
    public Fine getFine() {
        return fine;
    }

    /**
     * Sets the fine associated with this notification.
     *
     * @param fine The Fine entity.
     */
    public void setFine(Fine fine) {
        this.fine = fine;
    }

    /**
     * Gets the user associated with this notification.
     *
     * @return The User entity.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user associated with this notification.
     *
     * @param user The User entity.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the book associated with this notification.
     *
     * @return The Book entity.
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book associated with this notification.
     *
     * @param book The Book entity.
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the date and time when the reminder for this notification should occur.
     *
     * @return The reminder date and time.
     */
    public LocalDateTime getReminderDate() {
        return reminderDate;
    }

    /**
     * Sets the date and time when the reminder for this notification should occur.
     *
     * @param reminderDate The reminder date and time.
     */
    public void setReminderDate(LocalDateTime reminderDate) {
        this.reminderDate = reminderDate;
    }

    /**
     * Gets the message associated with this notification.
     * This can be a message informing the user of a fine, book due date, or other relevant info.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message for this notification.
     *
     * @param message The message.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
