package com.kaytmazvolkan.api.DTO;

/**
 * FineUpdateDTO is a Data Transfer Object (DTO) used for updating fine-related information.
 * It contains the fine ID, which uniquely identifies the fine to be updated.
 *
 * This class is used to transfer the fine ID in API requests where a fine update is required.
 */
public class FineUpdateDTO {

    /**
     * The unique identifier of the fine that needs to be updated.
     * This field is used to identify which fine record should be modified in the system.
     *
     * Example: If the user has a fine for a late return of a book, this ID would be
     * used to locate the fine record to update it.
     */
    private long fine_id;

    /**
     * Getter for the fine ID.
     *
     * @return The unique identifier of the fine.
     */
    public long getFineId() {
        return fine_id;
    }

    /**
     * Setter for the fine ID.
     *
     * @param fine_id The unique identifier of the fine to be set.
     */
    public void setFineId(long fine_id) {
        this.fine_id = fine_id;
    }

    /**
     * Default constructor for FineUpdateDTO.
     * This constructor is typically used when creating an instance of the DTO to transfer data.
     */
    public FineUpdateDTO() {
        // Default constructor
    }

    /**
     * Overloaded constructor for FineUpdateDTO.
     * Allows creation of an instance with a specified fine ID.
     *
     * @param fine_id The unique identifier of the fine.
     */
    public FineUpdateDTO(long fine_id) {
        this.fine_id = fine_id;
    }

    /**
     * Override the toString() method to provide a string representation of this DTO.
     * This method is useful for debugging and logging purposes, as it allows you to quickly
     * see the contents of the FineUpdateDTO object.
     *
     * @return A string representation of the FineUpdateDTO object.
     */
    @Override
    public String toString() {
        return "FineUpdateDTO{" +
                "fine_id=" + fine_id +
                '}';
    }
}
