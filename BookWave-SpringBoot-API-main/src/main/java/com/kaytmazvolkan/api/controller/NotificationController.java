package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.DTO.NotificationCreateDTO;
import com.kaytmazvolkan.api.DTO.NotificationDTO;
import com.kaytmazvolkan.api.DTO.NotificationUpdateDTO;
import com.kaytmazvolkan.api.service.NotificationService;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all notification-related requests.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * Constructor for NotificationController to inject the NotificationService.
     *
     * @param notificationService the NotificationService to handle notification logic.
     */
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Get all notifications from the database.
     *
     * @return List of Notification objects.
     */
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        // Fetches all notifications and returns them in the response body with a 200 OK status
        List<NotificationDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    /**
     * Get a specific notification by its ID.
     *
     * @param id the ID of the notification.
     * @return the found Notification.
     * @throws ResourceNotFoundException if the notification is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable Long id) {
        try {
            // Call the service to retrieve the notification details by its ID
            NotificationDTO notificationDTO = notificationService.getNotificationById(id);

            // Return the notification details with a 200 OK status if found
            return ResponseEntity.ok(notificationDTO);

        } catch (ResourceNotFoundException ex) {
            // Prepare an error response in case the notification with the given ID is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());

            // Return a 404 NOT FOUND status with the error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Create a new notification.
     *
     * @param notificationCreateRequest The notification creation details (book ID, fine ID, etc.).
     * @return ResponseEntity with the created notification.
     */
    @PostMapping
    public ResponseEntity<?> createNotificationBorrow(@RequestBody NotificationCreateDTO notificationCreateRequest) {

        // Validate input parameters
        if (notificationCreateRequest.getFineId() <= 0 || notificationCreateRequest.getBookId() <= 0 || notificationCreateRequest.getUsername() == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid input data. All fields must be valid.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            // Call the service to create the notification
            NotificationDTO createdNotification = notificationService.createNotificationBorrow(notificationCreateRequest);

            // Prepare the success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Notification created successfully.");
            response.put("notification", createdNotification);

            // Return the created notification with a 201 Created status
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (ResourceNotFoundException ex) {
            // Handle the case where a fine, book, or user is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Update an existing notification by its ID.
     *
     * @param id the ID of the notification to update.
     * @param notificationDetails the updated notification details (from the request body).
     * @return the updated notification wrapped in a ResponseEntity with a 200 OK status.
     * @throws ResourceNotFoundException if the notification with the specified ID is not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNotification(@PathVariable long id, @RequestBody NotificationUpdateDTO notificationDetails) {
        try {
            // Call the service to update the notification
            NotificationDTO updatedNotification = notificationService.updateNotification(id, notificationDetails);

            // Return the updated NotificationDTO
            return ResponseEntity.ok(updatedNotification);
        } catch (ResourceNotFoundException e) {
            // Handle resource not found exceptions
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            // Handle invalid input errors (e.g., validation errors)
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            // Handle unexpected errors
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Deletes a notification from the database based on the provided ID.
     *
     * @param id the ID of the notification to delete.
     * @return a response entity containing the result of the deletion operation.
     *         The response will include a map indicating whether the deletion was successful.
     * @throws ResourceNotFoundException if no notification with the specified ID exists.
     * @throws IllegalArgumentException if the ID provided is invalid (e.g., negative ID).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNotification(@PathVariable Long id) {
        try {
            // Attempt to delete the notification with the provided ID
            Map<String, Boolean> result = notificationService.deleteNotification(id);

            // Prepare a response with the result of the deletion operation
            Map<String, Object> response = new HashMap<>(result);

            // Return a 200 OK status with the result of the deletion
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // Handle the case when the notification is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);

            // Return a 404 NOT FOUND status with the error response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle the case when the input argument is invalid
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);

            // Return a 400 BAD REQUEST status with the error response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
