package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Author;
import com.kaytmazvolkan.api.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all author-related requests.
 */
@RestController
@RequestMapping("api/authors")
public class AuthorController {

    private final AuthorService authorService;

    // Constructor to inject services
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Get all authors from the database.
     *
     * @return List of authors.
     */
    @GetMapping
    public List<Author> getAllAuthors() {
        // Call the service layer to retrieve all authors
        return authorService.getAllAuthors();
    }

    /**
     * Create a new author.
     *
     * @param author the author to create. The author's details are provided in the request body.
     * @return a ResponseEntity containing a message and the created author, or an error message.
     * @throws IllegalArgumentException if the author already exists in the system.
     */
    @PostMapping
    public ResponseEntity<Object> createAuthor(@RequestBody Author author) {
        try {
            // Create a new author using the authorService
            Author createdAuthor = authorService.createAuthor(author);

            // Prepare the success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Author created successfully");
            response.put("author", createdAuthor);

            // Return the created author with a 201 Created status
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // If the author already exists or there is some validation issue, return a 400 Bad Request error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            // Return a 400 Bad Request response with the error details
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get a specific author by their ID.
     *
     * @param id the ID of the author to retrieve.
     * @return a ResponseEntity containing either the author details (if found) or an error message (if not found).
     * @throws ResourceNotFoundException if no author is found with the provided ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        try {
            // Retrieve the author from the service
            return ResponseEntity.ok(authorService.getAuthorById(id)); // Return the author in the response body

        } catch (ResourceNotFoundException ex) {
            // If author is not found, return a 404 Not Found response with the error message
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Update an existing author by their ID.
     *
     * @param id the ID of the author to update.
     * @param authorDetails the updated details of the author to be applied.
     * @param result the result of validation, which holds any errors that occurred during validation.
     * @return a response entity containing either the updated author or an error message.
     *         If successful, it returns the updated author; otherwise, it returns an error message.
     * @throws ResourceNotFoundException if the author with the given ID is not found.
     * @throws IllegalArgumentException if the updated author details are invalid or cause a conflict.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author authorDetails, BindingResult result) {
        if (result.hasErrors()) {
            // Return bad request with validation errors
            Map<String, Object> validationErrors = new HashMap<>();
            validationErrors.put("errors", result.getAllErrors());
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            // Try to update the author using the service
            Author updatedAuthor = authorService.updateAuthor(id, authorDetails);

            // Return the updated author in the response body
            return ResponseEntity.ok(updatedAuthor);

        } catch (ResourceNotFoundException e) {
            // Handle the case where the author is not found in the database
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle the case where the updated author details are invalid
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Delete an author by their ID.

     * @param id the ID of the author to delete.
     * @return a response entity indicating the result of the deletion operation. If successful, the response contains a confirmation of deletion;
     *         if not, an error message is provided based on the cause (e.g., author not found or author associated with books).
     * @throws ResourceNotFoundException if the author with the given ID is not found.
     * @throws IllegalArgumentException if the author is associated with any books and cannot be deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteAuthor(@PathVariable Long id) {
        try {
            // Attempt to delete the author and return a response indicating success
            Map<String, Boolean> result = authorService.deleteAuthor(id);
            Map<String, Object> response = new HashMap<>(result);
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // Handle case where author is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle case where author cannot be deleted due to associated books
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
