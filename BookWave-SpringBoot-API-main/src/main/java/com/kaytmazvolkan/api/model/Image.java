package com.kaytmazvolkan.api.model;

import jakarta.persistence.*;
import java.sql.Blob;
import java.util.Base64;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents an image entity with an ID and the image itself stored as a Blob.
 */
@Entity
@Table(name = "images")
public class Image {

    /**
     * Unique identifier for the image.
     * This field will be the primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", nullable = false, unique = true)
    private long image_id;

    /**
     * Image data stored as a Blob.
     */
    @Lob
    @Column(name = "image_data", nullable = false)
    private Blob image_data;

    /**
     * Default constructor required by JPA.
     */
    public Image() {}

    /**
     * Constructor to initialize the Image with an ID and image data.
     *
     * @param image_data The Blob data of the image.
     */
    public Image(Blob image_data) {
        this.image_data = image_data;
    }

    /**
     * Gets the ID of the image.
     *
     * @return The image ID.
     */
    public long getId() {
        return image_id;
    }

    /**
     * Sets the ID of the image.
     *
     * @param image_id The image ID.
     */
    public void setId(int image_id) {
        this.image_id = image_id;
    }

    /**
     * Gets the image data stored as a Blob.
     *
     * @return The image Blob.
     */
    @JsonIgnore // Ensure Jackson does not serialize the Blob directly
    public Blob getImage() {
        return image_data;
    }

    /**
     * Sets the image data.
     *
     * @param image_data The image data as a Blob.
     */
    public void setImage(Blob image_data) {
        this.image_data = image_data;
    }

    /**
     * Gets the image data as a base64-encoded string for JSON serialization.
     * This method is used to convert the image data into a format that can be safely serialized into JSON.
     *
     * @return A base64-encoded string representing the image data.
     */
    public String getImageBase64() {
        if (image_data != null) {
            try {
                byte[] byteArray = image_data.getBytes(1, (int) image_data.length());
                return Base64.getEncoder().encodeToString(byteArray);
            } catch (Exception e) {
                e.printStackTrace(); // Handle exceptions related to Blob processing
            }
        }
        return null;
    }
}
