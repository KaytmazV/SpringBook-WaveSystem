package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.User;
import com.kaytmazvolkan.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling business logic related to Users.
 * Provides CRUD operations for managing users in the system.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserService.
     *
     * @param userRepository the UserRepository to interact with the database.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieve all users from the database.
     *
     * @return List of {@link User} objects containing details of all users.
     */
    public List<User> getAllUsers() {
        // Returns a list of all users in the system
        return userRepository.findAll();
    }

    /**
     * Checks if a user already exists by their username.
     *
     * @param username the username of the user to check.
     * @return true if the user exists, false otherwise.
     */
    public boolean doesUserExist(String username) {
        // Returns true if the user exists, false if not
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Creates a new user in the database.
     *
     * @param user the user object to be created and stored in the database.
     * @return the saved {@link User} object.
     * @throws IllegalArgumentException if the username already exists in the database.
     */
    public User createUser(User user) {
        // Check if the user already exists by username
        if (doesUserExist(user.getUsername())) {
            // Throw an IllegalArgumentException if username already exists
            throw new IllegalArgumentException("Username already exists");
        }
        // Save and return the user if the username doesn't exist
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user to retrieve.
     * @return the {@link User} object if found.
     * @throws ResourceNotFoundException if the user is not found with the given username.
     */
    public User getUserByUsername(String username) {
        // Use the repository to find a user by their username
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    /**
     * Updates an existing user's details.
     *
     * @param username the username of the user to update.
     * @param userDetails the updated {@link User} object containing new data to be updated.
     * @return the updated {@link User} object.
     * @throws ResourceNotFoundException if the user is not found by the given username.
     */
    public User updateUser(String username, User userDetails) {
        // Retrieve the existing user by username
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist with username: " + username));

        // Update the fields with the new values from the userDetails object
        existingUser.setFirstName(userDetails.getFirstName());
        existingUser.setLastName(userDetails.getLastName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhoneNumber(userDetails.getPhoneNumber());
        existingUser.setAddress(userDetails.getAddress());
        existingUser.setRole(userDetails.getRole());
        existingUser.setPassword(userDetails.getPassword());

        // Save the updated user to the database
        return userRepository.save(existingUser);
    }

    /**
     * Deletes a user by their username.
     *
     * @param username the username of the user to delete.
     * @return a map with the result of the deletion operation.
     * @throws ResourceNotFoundException if the user is not found with the given username.
     */
    public Map<String, Boolean> deleteUser(String username) {
        // Find the user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Delete the user from the database
        userRepository.delete(user);

        // Return a response indicating the user was deleted successfully
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    /**
     * Logs in a user by checking their username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return `true` if the username and password match, indicating a successful login.
     * @throws ResourceNotFoundException If no user is found with the provided username.
     * @throws IllegalArgumentException If the provided password does not match the stored password for the user.
     */
    public boolean logIn(String username, String password) {
        // Find the user by the provided username
        User user = getUserByUsername(username);

        // Check if the user exists
        if (user == null) {
            // If no user is found with the given username, throw an exception
            throw new ResourceNotFoundException("User not found with username: " + username);
        }

        // Check if the provided password matches the stored password for the user
        if (!user.getPassword().equals(password)) {
            // If the password doesn't match, throw an exception
            throw new IllegalArgumentException("Wrong password");
        }

        // If both username and password match, return true indicating a successful login
        return true;
    }
}
