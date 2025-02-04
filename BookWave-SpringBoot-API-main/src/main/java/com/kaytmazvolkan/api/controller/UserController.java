package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.DTO.LogInDTO;
import com.kaytmazvolkan.api.model.User;
import com.kaytmazvolkan.api.service.UserService;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all user-related requests.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor to inject services
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get all users from the database.
     *
     * @return List of users.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Get a specific user by their username.
     *
     * @param username the username of the user.
     * @return the found user.
     * @throws ResourceNotFoundException if the user is not found.
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(userService.getUserByUsername(username));
        } catch (ResourceNotFoundException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Create a new user in the database.
     *
     * @param user the user to create.
     * @return the saved user.
     */
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User created successfully");
            response.put("user", createdUser);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Update an existing user by username.
     *
     * @param username    the username of the user to update.
     * @param userDetails the new details for the user.
     * @return the updated user.
     * @throws ResourceNotFoundException if the user is not found.
     */
    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(
            @PathVariable String username, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(username, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Delete a user by their username.
     *
     * @param username the username of the user to delete.
     * @return a response indicating the result.
     * @throws ResourceNotFoundException if the user is not found.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String username) {
        try {
            Map<String, Boolean> result = userService.deleteUser(username);
            Map<String, Object> response = new HashMap<>(result);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Endpoint to log in a user by validating their username and password.
     *
     * This method receives a `LogInDTO` containing the user's credentials,
     * attempts to authenticate the user, and returns a response indicating success or failure.
     *
     * @param loginDTO The Data Transfer Object (DTO) containing the user's login credentials.
     * @return A `ResponseEntity` containing either a success message or an error message based on authentication outcome.
     */
    @PostMapping("/logIn")
    public ResponseEntity<Object> loginIn(@RequestBody LogInDTO loginDTO) {
        try {
            // Call the service to authenticate the user with the provided username and password
            boolean success = userService.logIn(loginDTO.getUsername(), loginDTO.getPassword());

            // Prepare the success response with a message indicating the user logged in successfully
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User logged in successfully");

            // Return a 200 OK response with the success message
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // Handle case where the user is not found or the username is incorrect
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            // Return a 404 NOT_FOUND response with the error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle case where the password does not match or another login error occurs
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            // Return a 400 BAD_REQUEST response with the error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
