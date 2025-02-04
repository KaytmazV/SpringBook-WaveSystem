package com.kaytmazvolkan.api.DTO;

import com.kaytmazvolkan.api.model.Comment;

/**
 * Data Transfer Object (DTO) representing a comment on a book.
 * This class is used to transfer comment data in a simplified format.
 * It does not include all the fields of the original `Comment` entity but focuses on the fields that are required for responses.
 */
public class CommentDTO {

    private Long bookId;                  // The ID of the book associated with the comment
    private String commentDescription;   // The description or text content of the comment
    private int rating;                 // The rating (1 to 5 stars) given to the book
    private long id;                   // The unique ID of the comment

    /**
     * Default constructor required for frameworks like Jackson to deserialize the object.
     */
    public CommentDTO() {
    }

    /**
     * Constructor that maps from a `Comment` entity to a `CommentDTO`.
     * Extracts relevant fields from the `Comment` object to create a simplified DTO.
     *
     * @param comment the `Comment` entity to map from
     */
    public CommentDTO(Comment comment) {
        this.bookId = comment.getBook() != null ? comment.getBook().getBook_id() : null;
        this.commentDescription = comment.getCommentDescription();
        this.rating = comment.getRating();
        this.id = comment.getCommentId();
    }

    // Getters and setters

    /**
     * Gets the unique ID of the comment.
     *
     * @return the ID of the comment
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique ID of the comment.
     *
     * @param id the ID to set
     */

    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the book associated with this comment.
     *
     * @return the ID of the book
     */
    public Long getBookId() {
        return bookId;
    }

    /**
     * Sets the ID of the book associated with this comment.
     *
     * @param bookId the book ID to set
     */
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the description of the comment.
     *
     * @return the comment description
     */
    public String getCommentDescription() {
        return commentDescription;
    }

    /**
     * Sets the description of the comment.
     *
     * @param commentDescription the description to set
     */
    public void setCommentDescription(String commentDescription) {
        this.commentDescription = commentDescription;
    }

    /**
     * Gets the rating given to the book.
     *
     * @return the rating (1 to 5)
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating for the book.
     *
     * @param rating the rating to set (1 to 5)
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
