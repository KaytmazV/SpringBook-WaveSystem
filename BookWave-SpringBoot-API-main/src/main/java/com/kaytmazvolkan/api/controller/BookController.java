package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Book;
import com.kaytmazvolkan.api.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all book-related requests.
 */
@RestController
@RequestMapping("api/books")
public class BookController {

    private final BookService bookService;

    // Constructor to inject services
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Get all books from the database.
     *
     * @return List of books.
     */
    @GetMapping
    public List<Book> getAllBooks() {
        // Call the service layer to retrieve all books
        return bookService.getAllBooks();
    }

    /**
     * Retrieves a specific book by its ID.
     *
     * @param id the ID of the book to retrieve.
     * @return a ResponseEntity containing either the book details or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try {
            // Attempt to retrieve the book by its ID
            return ResponseEntity.ok(bookService.getBookById(id));

        } catch (ResourceNotFoundException ex) {
            // Handle case where the book is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());

            // Return error response with HTTP 404 status (Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete.
     * @return a ResponseEntity containing either a success message or an error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable Long id) {
        try {
            // Attempt to delete the book by its ID
            Map<String, Boolean> result = bookService.deleteBook(id);
            Map<String, Object> response = new HashMap<>(result);

            // Return success response with HTTP 200 status
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // Handle case where the book is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // Handle case where the provided ID is invalid
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);

            // Return error response with HTTP 400 status (Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Creates a new book in the system.
     *
     * @param book the book object to be created.
     * @return a ResponseEntity containing either the created book with success message or an error message.
     */
    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody Book book) {
        try {
            // Call the service to create the book
            Book createdBook = bookService.createBook(book);

            // Prepare a response map with success message
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book created successfully");
            response.put("book", createdBook);

            // Return the response with CREATED status (HTTP 201)
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (ResourceNotFoundException e) {
            // In case of error (like book already exists or author/genre/image validation fails)
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage()); // Include error message from the service

            // Return the error response with BAD_REQUEST status (HTTP 400)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            // Catch any other unexpected exceptions
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred");

            // Return a generic error message with INTERNAL_SERVER_ERROR status (HTTP 500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Updates an existing book in the system based on the provided book details.
     *
     * @param id          the ID of the book to update.
     * @param bookDetails the new details for the book to update.
     * @param result      the result of the validation check for the book data.
     * @return a ResponseEntity containing either the updated book or an error message.
     * @throws ResourceNotFoundException if the book with the given ID is not found.
     * @throws IllegalArgumentException  if there are issues with the provided book data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable Long id, @Valid @RequestBody Book bookDetails, BindingResult result) {
        if (result.hasErrors()) {
            // Return bad request with validation errors
            Map<String, Object> validationErrors = new HashMap<>();
            validationErrors.put("errors", result.getAllErrors());
            return ResponseEntity.badRequest().body(validationErrors);
        }

        try {
            // Attempt to update the book with the given ID and details
            Book updatedBook = bookService.updateBook(id, bookDetails);

            // Return the updated book with a 200 OK status
            return ResponseEntity.ok(updatedBook);

        } catch (ResourceNotFoundException e) {
            // If the book is not found, return a 404 Not Found with the error message
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (IllegalArgumentException e) {
            // If there is an issue with the provided book data, return a 400 Bad Request
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
