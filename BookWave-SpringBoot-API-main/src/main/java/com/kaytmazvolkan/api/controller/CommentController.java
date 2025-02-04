package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.DTO.CommentDTO;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Comment;
import com.kaytmazvolkan.api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all comment-related requests.
 */
@RestController
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    // Constructor to inject services
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * Get all comments from the database.
     *
     * @return List of comments.
     */
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        // Retrieve all comments from the service layer and converts them to CommentDTO objects.
        List<CommentDTO> comments = commentService.getAllComments();

        // Return the list of CommentDTOs
        return ResponseEntity.ok(comments);

    }

    /**
     * Create a new comment.
     *
     * @param comment the comment to create.
     * @return the created comment.
     */
    @PostMapping
    public ResponseEntity<?> createComment(@Valid @RequestBody Comment comment) {
        try {
            // Log the incoming comment for debugging
            System.out.println("Incoming Comment: " + comment);

            // Save the comment using the service layer
            Comment savedComment = commentService.createComment(comment);

            // Prepare response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Comment created successfully");
            response.put("comment", savedComment);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            // Handle case where a resource (like Book) is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle illegal argument issues
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {

            // Generic error handling
            e.printStackTrace(); // Log the stack trace for debugging
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Get a specific comment by their ID.
     *
     * @param id the ID of the comment.
     * @return the comment.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
        try {
            // Call the service to get the CommentDTO
            CommentDTO commentDTO = commentService.getCommentById(id);
            return ResponseEntity.ok(commentDTO);

        } catch (ResourceNotFoundException ex) {
            // Prepare an error response in case of a missing comment
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Update an existing comment.
     *
     * @param id             the ID of the comment to update.
     * @param commentDetails the updated comment details.
     * @param result         the result of validation.
     * @return the updated comment or an error message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable Long id, @Valid @RequestBody Comment commentDetails, BindingResult result) {
        if (result.hasErrors()) {
            // Return bad request with validation errors
            Map<String, Object> validationErrors = new HashMap<>();
            validationErrors.put("errors", result.getAllErrors());
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            // Attempt to update the comment using the service layer method
            Comment updatedComment = commentService.updateComment(id, commentDetails);

            // If the update is successful, return the updated comment with a 200 OK status
            return ResponseEntity.ok(updatedComment);

        } catch (ResourceNotFoundException e) {
            // If the comment with the provided ID is not found, handle it by returning a 404 Not Found response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // If the update fails due to invalid data (e.g., invalid book ID), return a 400 Bad Request response
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Delete a comment by their ID.
     *
     * @param id the ID of the comment to delete.
     * @return a response indicating the result of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteComment(@PathVariable Long id) {
        try {
            // Attempt to delete the comment by calling the service layer
            Map<String, Boolean> result = commentService.deleteComment(id);

            // Create a response map with the deletion result (true if deleted successfully)
            Map<String, Object> response = new HashMap<>(result);

            // Return the response with HTTP status 200 OK if deletion is successful.
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // If the comment with the given ID is not found, handle it with an error message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);

            // Return HTTP status 404 Not Found with the error message and deletion status.
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // If the deletion fails due to invalid data or other argument-related issues
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);

            // Return HTTP status 400 Bad Request with the error message and deletion status.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
