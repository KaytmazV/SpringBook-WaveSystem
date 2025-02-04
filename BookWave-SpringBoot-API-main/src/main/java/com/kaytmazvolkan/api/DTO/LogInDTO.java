package com.kaytmazvolkan.api.DTO;

/**
 * LogInDTO is a Data Transfer Object (DTO) used for transferring login credentials.
 * It contains the user's username and password, which are required for authentication.
 *
 * This class is used in API requests where a user needs to log in to the system.
 */
public class LogInDTO {

    /**
     * The username of the user attempting to log in.
     * This is used to identify the user during the authentication process.
     */
    private String username;

    /**
     * The password of the user attempting to log in.
     * This is used to verify the identity of the user during the authentication process.
     */
    private String password;

    /**
     * Getter for the username.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     *
     * @param username The username of the user to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password.
     *
     * @param password The password of the user to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Default constructor for LogInDTO.
     * This constructor is typically used when creating an instance of this DTO to transfer login credentials.
     */
    public LogInDTO() {
        // Default constructor
    }

    /**
     * Overloaded constructor for LogInDTO.
     * Allows creation of an instance with a specified username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     */
    public LogInDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Override the toString() method to provide a string representation of this DTO.
     * This method is useful for debugging and logging purposes.
     *
     * @return A string representation of the LogInDTO object.
     */
    @Override
    public String toString() {
        return "LogInDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
