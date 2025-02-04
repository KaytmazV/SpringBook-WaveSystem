package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Genre;
import com.kaytmazvolkan.api.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all genre-related requests.
 */
@RestController
@RequestMapping("api/genres")
public class GenreController {

    private final GenreService genreService;

    // Constructor to inject services
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Get all genres from the database.
     *
     * @return a list of all genres stored in the database.
     */
    @GetMapping
    public List<Genre> getAllGenres() {
        // Call the service layer to retrieve all genres
        return genreService.getAllGenres();
    }

    /**
     * Create a new genre.
     *
     * @param genre the genre to create (provided in the request body).
     * @return a response containing the result of the creation operation.
     */
    @PostMapping
    public ResponseEntity<Object> createGenre(@Valid @RequestBody Genre genre) {
        try {
            // Call the service method to create the genre
            Genre createdGenre = genreService.createGenre(genre);

            // Prepare the success response with the created genre details
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Genre created successfully");
            response.put("genre", createdGenre);

            // Return HTTP 201 Created with the response body
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {
            // Handle case where the genre creation fails
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            // Return HTTP 400 Bad Request with the error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Get a specific genre by its ID
     *
     * @param id the ID of the genre to retrieve.
     * @return the genre if found, or an error message if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGenreById(@PathVariable Long id) {
        try {
            // Call the service method to retrieve the genre by ID
            return ResponseEntity.ok(genreService.getGenreById(id));

        } catch (ResourceNotFoundException ex) {
            // Handle case where the genre with the specified ID is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());

            // Return HTTP 404 Not Found with the error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Update an existing genre by its ID.
     *
     * @param id            the ID of the genre to update.
     * @param genreDetails  the updated genre details, provided in the request body.
     * @param result        the result of the validation performed on the input.
     * @return the updated genre object if the update is successful, or an error message in case of failure.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateGenre(@PathVariable Long id, @Valid @RequestBody Genre genreDetails, BindingResult result) {
        // Check if there are validation errors in the genre details
        if (result.hasErrors()) {
            // Return bad request with validation errors
            Map<String, Object> validationErrors = new HashMap<>();
            validationErrors.put("errors", result.getAllErrors());
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            // Call the service method to update the genre in the database
            Genre updatedGenre = genreService.updateGenre(id, genreDetails);

            // Return HTTP 200 OK with the updated genre details
            return ResponseEntity.ok(updatedGenre);

        } catch (ResourceNotFoundException e) {
            // Handle case where the genre with the given ID is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            // Return HTTP 404 Not Found if the genre is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle case where the genre details violate constraints
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());

            // Return HTTP 400 Bad Request if there is an issue with the genre update
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Deletes a genre from the database by its ID.
     *
     * @param id the ID of the genre to delete.
     * @return a response indicating the result of the deletion.
     *         If successful, returns a map with the key "deleted" set to true.
     *         If there was an error, returns an error message with the appropriate status code.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteGenre(@PathVariable Long id) {
        try {
            // Call the service layer to delete the genre by its ID and get the deletion result
            Map<String, Boolean> result = genreService.deleteGenre(id);

            // Prepare the response map containing the deletion status
            Map<String, Object> response = new HashMap<>(result);

            // Return HTTP 200 OK if the genre is successfully deleted.
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // Handle case where the genre with the provided ID is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);

            // Return HTTP 404 Not Found if the genre is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle case where the genre is associated with books and cannot be deleted
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);

            // Return HTTP 400 Bad Request if the genre cannot be deleted due to associations.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
