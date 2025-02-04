package com.kaytmazvolkan.api.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing a User in the database.
 */
@Table(name = "user")
@Entity
public class User {

    /**
     * Unique identifier for the user, used as the primary key in the database.
     * The username must be unique for every user.
     */
    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;  // Use 'username' as the unique identifier

    /**
     * The first name of the user.
     * This field is required and cannot be null.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * The last name of the user.
     * This field is required and cannot be null.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * The email address of the user.
     * This field is required and cannot be null.
     * The email must be unique across all users in the system.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * The phone number of the user.
     * This field is optional and can be left null.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * The physical address of the user.
     * This field is optional and can be left null.
     */
    @Column(name = "address")
    private String address;

    /**
     * The role assigned to the user (e.g., "ADMIN", "USER", "LIBRARIAN").
     * This field is required and cannot be null.
     */
    @Column(name = "role", nullable = false)
    private String role;

    /**
     * The date and time when the user registered in the system.
     * This field is automatically set to the current date and time upon user creation.
     */
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registration_date;

    /**
     * The password of the user.
     * This field is required and cannot be null.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Default constructor.
     * Required by JPA for entity instantiation.
     * Automatically sets the registration date to the current date and time.
     */
    public User() {
        this.registration_date = LocalDateTime.now();
    }

    /**
     * Constructor to create a new User with specified attributes.
     * The registration date is automatically set to the current date and time upon creation.
     *
     * @param username    The unique username for the user.
     * @param firstName   The first name of the user.
     * @param lastName    The last name of the user.
     * @param email       The email address of the user.
     * @param phoneNumber The phone number of the user (optional).
     * @param address     The address of the user (optional).
     * @param role        The role of the user (e.g., "ADMIN", "USER", "LIBRARIAN").
     * @param password    The password of the user.
     */
    public User(String username, String firstName, String lastName, String email, String phoneNumber, String address, String role, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
        this.password = password;
        this.registration_date = LocalDateTime.now(); // Automatically set to current time
    }

    // Getters and setters

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username to set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The new first name for the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The new last name for the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The new email address for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the user.
     *
     * @return The phone number of the user, or null if not provided.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the user.
     *
     * @param phoneNumber The new phone number for the user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the address of the user.
     *
     * @return The address of the user (optional).
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user.
     *
     * @param address The new address for the user.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the role of the user.
     * Roles may include "ADMIN", "USER"
     *
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     *
     * @param role The new role for the user.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets the registration date and time of the user.
     *
     * @return The registration date and time of the user.
     */
    public LocalDateTime getRegistrationDate() {
        return registration_date;
    }

    /**
     * Sets the registration date and time of the user.
     *
     * @param registrationDate The new registration date and time.
     */
    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registration_date = registrationDate;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
