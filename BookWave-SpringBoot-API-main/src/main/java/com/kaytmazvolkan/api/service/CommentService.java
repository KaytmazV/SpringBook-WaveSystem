package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.DTO.CommentDTO;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Comment;
import com.kaytmazvolkan.api.repository.BookRepository;
import com.kaytmazvolkan.api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to Books.
 * Provides CRUD operations for managing books in the system.
 */
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;

    /**
     * The constructor for the CommentService class.
     *
     * @param commentRepository The repository responsible for interacting with Comment data.
     * @param bookRepository The repository responsible for interacting with Book data.
     */
    @Autowired
    public CommentService(CommentRepository commentRepository, BookRepository bookRepository) {
        this.commentRepository = commentRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieve all comments from the database.
     *
     * @return a list of all comments.
     */
    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(CommentDTO::new) // Convert each Comment to CommentDTO
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a specific comment by its ID.
     *
     * @param id the ID of the comment to retrieve.
     * @return the found comment.
     * @throws ResourceNotFoundException if no comment with the given ID is found.
     */
    public CommentDTO getCommentById(Long id) {
        // Fetch the Comment entity by ID or throw an exception if not found
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        // Convert the Comment entity to CommentDTO
        return new CommentDTO(comment);
    }

    /**
     * Check if a comment already exists in the database.
     *
     * @param comment the comment to check.
     * @return true if the comment exists, false otherwise.
     */
    public boolean doesCommentExist(Comment comment) {
        // Check if the comment is null or has an invalid ID
        if (comment == null || comment.getCommentId() == null) {
            return false;
        }

        // If a comment is found by id, it returns true, otherwise false
        return commentRepository.findById(comment.getCommentId()).isPresent();
    }

    /**
     * Creates and saves a new comment for a book.
     *
     * @param comment The comment to be created and saved.
     * @return The saved Comment object after it has been persisted.
     * @throws IllegalArgumentException if the book associated with the comment does not exist.
     */
    public Comment createComment(Comment comment) {
        // Check if the book associated with the comment exists in the repository
        if ((bookRepository.findById(comment.getBook().getBook_id()) == null)) {
            // If the book does not exist, throw an IllegalArgumentException
            throw new IllegalArgumentException("Book does not exist");
        }

        // Retrieve the book from the repository to ensure it is a valid reference
        comment.setBook(bookRepository.findById(comment.getBook().getBook_id()).get());

        // Save the comment to the repository and return the saved comment
        return commentRepository.save(comment);
    }

    /**
     * Updates an existing comment with new details.
     *
     * @param id The ID of the comment to be updated.
     * @param commentDetails A `Comment` object containing the new details to update the existing comment.
     * @return The updated `Comment` object, after it has been saved to the repository.
     * @throws ResourceNotFoundException If no comment is found with the given `id`.
     * @throws IllegalArgumentException If the book associated with the updated comment does not exist.
     */
    public Comment updateComment(Long id, Comment commentDetails) {
        // Fetch the existing comment to update by its ID
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment does not exist with id: " + id));

        // Check if the book associated with the commentDetails exists in the repository
        if (!(bookRepository.findById(commentDetails.getBook().getBook_id()).isPresent())) {
            throw new IllegalArgumentException("Book does not exist");
        }

        // Update the existing comment with the new details provided in commentDetails.
        existingComment.setCommentDescription(commentDetails.getCommentDescription());
        existingComment.setRating(commentDetails.getRating());

        // Retrieve the book from the repository to associate with the updated comment
        existingComment.setBook(bookRepository.findById(commentDetails.getBook().getBook_id()).get());

        // Save and return the updated comment
        return commentRepository.save(existingComment);
    }

    /**
     * Delete a comment by its ID.
     *
     * @param id the ID of the comment to delete.
     * @return a map indicating the deletion status.
     * @throws ResourceNotFoundException if no comment with the given ID is found.
     */
    public Map<String, Boolean> deleteComment(Long id) {
        // Find the comment to delete
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));

        // Delete the comment from the repository
        commentRepository.delete(comment);

        // Return a response indicating that the comment was deleted
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;  // Return the response
    }
}
