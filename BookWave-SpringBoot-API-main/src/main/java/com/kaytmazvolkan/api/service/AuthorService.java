package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Author;
import com.kaytmazvolkan.api.model.Book;
import com.kaytmazvolkan.api.repository.AuthorRepository;
import com.kaytmazvolkan.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling business logic related to Authors.
 * Provides CRUD operations for managing authors in the system.
 */
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    /**
     * Constructor for AuthorService.
     *
     * @param authorRepository the AuthorRepository to interact with the database.
     * @param bookRepository   the BookRepository to check if author has associated books.
     */
    @Autowired
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Get all authors from the database.
     *
     * @return List of authors.
     */
    public List<Author> getAllAuthors() {
        // Retrieves a list of all authors from the database
        return authorRepository.findAll();
    }

    /**
     * Get a specific author by their ID.
     *
     * @param id the ID of the author.
     * @return the found author.
     * @throws ResourceNotFoundException if the author is not found.
     */
    public Author getAuthorById(Long id) {
        // Fetch the author entity by ID or throw an exception if not found
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    /**
     * Checks if an author already exists by their first and last name.
     *
     * @param author the author to check.
     * @return true if the author exists, false otherwise.
     */
    public boolean doesAuthorExist(Author author) {
        // Check if the author or their first name or last name is null
        if(author == null || author.getFirstName() == null || author.getLastName() == null) {
            return false;
        }

        // If an author is found, it returns true, otherwise false
        return authorRepository.findByFirstNameAndLastName(
                author.getFirstName(), author.getLastName()).isPresent();
    }

    /**
     * Create a new author in the database.
     *
     * @param author the author to create.
     * @return the saved author.
     * @throws IllegalArgumentException if the author already exists.
     */
    public Author createAuthor(Author author) {
        // Check if the user already exists by username
        if (doesAuthorExist(author)) {
            // Throw an IllegalArgumentException if username already exists
            throw new IllegalArgumentException("Author already exists");
        }

        // Save the user if the username doesn't exist
        return authorRepository.save(author);
    }

    /**
     * Update an existing author.
     *
     * @param id             the ID of the author to update.
     * @param authorDetails  the updated author details.
     * @return the updated author.
     * @throws ResourceNotFoundException if the author is not found.
     * @throws IllegalArgumentException if the updated author details already exist.
     */
    public Author updateAuthor(Long id, Author authorDetails) {
        // Fetch the existing author
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author does not exist with id: " + id));

        // Check if the new author details conflict with an existing author
        if (!existingAuthor.getFirstName().equalsIgnoreCase(authorDetails.getFirstName()) && !existingAuthor.getLastName().equalsIgnoreCase(authorDetails.getLastName()) && doesAuthorExist(authorDetails)) {
            throw new IllegalArgumentException("Author already exists");
        }

        // Update the existing author's details
        existingAuthor.setAuthor(authorDetails.getFirstName(), authorDetails.getLastName());

        // Save and return the updated author
        return authorRepository.save(existingAuthor);
    }

    /**
     * Delete an author by their ID.
     *
     * @param id the ID of the author to delete.
     * @return a map indicating whether the deletion was successful.
     * @throws ResourceNotFoundException if the author is not found.
     * @throws IllegalArgumentException if the author has associated books and cannot be deleted.
     */
    public Map<String, Boolean> deleteAuthor(Long id) {
        // Fetch author by id
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));

        // Check if the author is associated with any books
        List<Book> booksWithAuthor = bookRepository.findByAuthorId(id);
        if (!booksWithAuthor.isEmpty()) {
            throw new IllegalArgumentException("Author is associated with one or more books and cannot be deleted.");
        }

        // Delete the author
        authorRepository.delete(author);

        // Return a simple response indicating the author was deleted
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;  // Return the response
    }
}
