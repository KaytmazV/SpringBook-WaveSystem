package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.DTO.NotificationCreateDTO;
import com.kaytmazvolkan.api.DTO.NotificationDTO;
import com.kaytmazvolkan.api.DTO.NotificationUpdateDTO;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;

import com.kaytmazvolkan.api.model.Book;
import com.kaytmazvolkan.api.model.Fine;
import com.kaytmazvolkan.api.model.Notification;
import com.kaytmazvolkan.api.model.User;
import com.kaytmazvolkan.api.repository.BookRepository;
import com.kaytmazvolkan.api.repository.FineRepository;
import com.kaytmazvolkan.api.repository.NotificationRepository;
import com.kaytmazvolkan.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to notifications.
 * Provides CRUD operations for managing notifications in the system.
 */
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final FineRepository fineRepository;

    /**
     * Constructor for the NotificationService class.
     *
     * @param notificationRepository the repository for accessing notification data.
     */
    @Autowired
    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository, BookRepository bookRepository,
                               FineRepository fineRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.fineRepository = fineRepository;
    }

    /**
     * Retrieves all notifications from the database and converts them into Data Transfer Objects (DTOs).
     *
     * @return List of `NotificationDTO` objects representing all notifications in the system.
     */
    public List<NotificationDTO> getAllNotifications() {
        // Fetch all notifications from the database using the repository
        List<Notification> notifications = notificationRepository.findAll();

        // Convert the list of Notification entities into NotificationDTOs and return the list
        return notifications.stream()
                .map(NotificationDTO::new) // Convert each Comment to CommentDTO
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a notification by its ID from the database and converts it into a Data Transfer Object (DTO).
     *
     * @param id the ID of the notification to retrieve.
     * @return `NotificationDTO` representing the fetched notification.
     * @throws ResourceNotFoundException if no notification with the specified ID exists in the database.
     */
    public NotificationDTO getNotificationById(Long id) {
        // Fetch the Notification entity by ID or throw an exception if not found
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        // Convert the Notification entity to NotificationDTO and return it
        return new NotificationDTO(notification);
    }

    /**
     * Creates a new notification for a user when they borrow a book.
     *
     * @param notificationCreateDTO the DTO containing the details required to create the notification, including the
     *                              book ID and username.
     * @return a {@link NotificationDTO} representing the newly created notification with relevant details.
     * @throws ResourceNotFoundException if any of the related entities (Book, User) are not found using the provided IDs.
     */
    public NotificationDTO createNotificationBorrow(NotificationCreateDTO notificationCreateDTO) {
        // Fetch the related entities using their IDs
        Book book = bookRepository.findById(notificationCreateDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + notificationCreateDTO.getBookId()));

        User user = userRepository.findByUsername(notificationCreateDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + notificationCreateDTO.getUsername()));

        // Create the new Notification entity (without fine)
        Notification notification = new Notification();
        notification.setBook(book);  // Associate the book
        notification.setUser(user);  // Associate the user
        notification.setMessage("Don't forget to return the book! ");
        notification.setReminderDate(LocalDateTime.now().plusWeeks(2));
    notification.setFine(null);

        // Save the notification to the repository
        notification = notificationRepository.save(notification);

        // Map the saved Notification entity to NotificationDTO and return
        return new NotificationDTO(notification);
    }

    /**
     * Creates a new notification for a user when a fine is added to their account.
     *
     * @param notificationCreateDTO the DTO containing the details required to create the notification, including the
     *                              book ID, username, and fine ID.
     * @return a {@link NotificationDTO} representing the newly created notification with relevant details.
     * @throws ResourceNotFoundException if any of the related entities (Book, User, Fine) are not found using the provided IDs.
     */
    public NotificationDTO createNotificationFine(NotificationCreateDTO notificationCreateDTO) {
        // Fetch the related entities using their IDs
        Book book = bookRepository.findById(notificationCreateDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + notificationCreateDTO.getBookId()));

        User user = userRepository.findByUsername(notificationCreateDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + notificationCreateDTO.getUsername()));

        Fine fine = fineRepository.findById(notificationCreateDTO.getFineId())
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + notificationCreateDTO.getFineId()));

        // Create the new Notification entity (without fine)
        Notification notification = new Notification();
        notification.setBook(book);  // Associate the book
        notification.setUser(user);  // Associate the user
        notification.setFine(fine); // Associate the fine
        notification.setMessage("A fine was added to your account. Dont Forget to pay it!");
        notification.setReminderDate(LocalDateTime.now());

        // Save the notification to the repository
        notification = notificationRepository.save(notification);

        // Map the saved Notification entity to NotificationDTO and return
        return new NotificationDTO(notification);
    }

    /**
     * Updates an existing notification based on the provided notification ID and update details.
     *
     * @param notificationId the ID of the notification to be updated.
     * @param notificationUpdateDTO the DTO containing the new values for the notification.
     * @return the updated {@link NotificationDTO} representing the updated notification.
     * @throws ResourceNotFoundException if any of the associated entities (Notification, Book, User, or Fine) are not found.
     * @throws IllegalArgumentException if any invalid input is provided for updating the notification.
     */
    public NotificationDTO updateNotification(long notificationId, NotificationUpdateDTO notificationUpdateDTO) {
        // Retrieve the Notification entity by notificationId
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + notificationId));

        // Retrieve the Book entity by bookId from the DTO
        Book book = bookRepository.findById(notificationUpdateDTO.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + notificationUpdateDTO.getBookId()));

        // Retrieve the User entity by username from the DTO
        User user = userRepository.findByUsername(notificationUpdateDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + notificationUpdateDTO.getUsername()));

        // Retrieve the Fine entity by fineId from the DTO
        Fine fine = fineRepository.findById(notificationUpdateDTO.getFineId())
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + notificationUpdateDTO.getFineId()));

        // Update the Notification entity with the new values from the DTO
        notification.setBook(book);
        notification.setUser(user);
        notification.setFine(fine);
        notification.setMessage(notificationUpdateDTO.getMessage()); // Corrected

        // Save the updated Notification entity
        notification = notificationRepository.save(notification);

        // Map the updated entity back to NotificationDTO and return it
        return new NotificationDTO(notification);
    }

    /**
     * Deletes a notification from the database based on its ID.
     *
     * @param id the ID of the notification to delete.
     * @return a map containing a boolean value indicating whether the notification was successfully deleted.
     * @throws ResourceNotFoundException if no notification with the specified ID exists in the database.
     */
    public Map<String, Boolean> deleteNotification(Long id) {
        // Find the Notification entity by ID or throw an exception if not found
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));

        // Delete the Notification from the repository
        notificationRepository.delete(notification);

        // Return a response indicating that the notification was deleted
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;  // Return the response
    }
}
