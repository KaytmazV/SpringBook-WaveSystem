package com.kaytmazvolkan.api.model;

import jakarta.persistence.*;

/**
 * Entity representing an Author in the database.
 */
@Table(name = "author")
@Entity
public class Author {

    /**
     * Unique identifier for the author, used as the primary key in the database.
     * The id must be unique for every author.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false)
    private Long author_id;  // Use Long instead of long to support null values

    /**
     * The first name of the author.
     * This field is required and cannot be null.
     */
    @Column(name = "first_name", nullable = false)
    private String first_name;

    /**
     * The last name of the author.
     * This field is required and cannot be null.
     */
    @Column(name = "last_name", nullable = false)
    private String last_name;

    /**
     * Default constructor required by JPA.
     */
    public Author() {
        this.first_name = "";
        this.last_name = "";
    }

    /**
     * Constructor to create a new Author with specified attributes.
     *
     * @param first_name   The first name of the author.
     * @param last_name    The last name of the author.
     */
    public Author(String first_name, String last_name) {
        setAuthor(first_name, last_name);
    }

    /**
     * Sets the first name and last name of the author.
     *
     * @param first_name The first name of the author.
     * @param last_name  The last name of the author.
     */
    public void setAuthor(String first_name, String last_name){
        setFirstName(first_name);
        setLastName(last_name);
    }

    // Getters and Setters

    /**
     * Gets the firstname of the author.
     *
     * @return The firstname of the author.
     */
    public String getFirstName() {
        return first_name;
    }

    /**
     * Sets the firstname of the auhtor.
     *
     * @param first_name The new firstname to set for the author.
     */
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Gets the lastname of the author.
     *
     * @return The lastname of the author.
     */
    public String getLastName() {
        return last_name;
    }

    /**
     * Sets the lastname of the auhtor.
     *
     * @param last_name The new lastname to set for the author.
     */
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Gets the id of the author.
     *
     * @return The id of the author.
     */
    public Long getAuthor_id() {
        return author_id;
    }

    /**
     * Sets the id of the auhtor.
     *
     * @param author_id The new id to set for the author.
     */
    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    // Override toString method for better object representation
    @Override
    public String toString() {
        return "Author{id=" + author_id + ", first_name='" + first_name + "', last_name='" + last_name + "'}";
    }
}
