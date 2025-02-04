package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Book;
import com.kaytmazvolkan.api.model.Genre;
import com.kaytmazvolkan.api.repository.BookRepository;
import com.kaytmazvolkan.api.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling business logic related to Genres.
 * Provides CRUD operations for managing genres in the system.
 */
@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    /**
     * Constructor for the GenreService class.
     *
     * @param genreRepository the repository for accessing genre data.
     * @param bookRepository  the repository for accessing book data.
     */
    @Autowired
    public GenreService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Get all genres from the database.
     *
     * @return List of genres.
     */
    public List<Genre> getAllGenres() {
        // Retrieves a list of all genres from the database
        return genreRepository.findAll();
    }

    /**
     * Get a specific genre by their ID.
     *
     * @param id the ID of the genre.
     * @return the found genre.
     * @throws ResourceNotFoundException if the genre is not found.
     */
    public Genre getGenreById(Long id) {
        // Fetch the transaction entity by ID or throw an exception if not found
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));
    }

    /**
     * Create a new genre in the database.
     *
     * @param genre the genre to create.
     * @return the saved genre.
     */
    public Genre createGenre(Genre genre) {
        // Check if the genre already exists by id
        if (doesGenreExist(genre.getType())) {
            // Throw an IllegalArgumentException if genre already exists
            throw new IllegalArgumentException("Genre already exists");
        }
        return genreRepository.save(genre);
    }

    /**
     * Checks if a genre exists by its type.
     *
     * @param type the genre type to check for existence
     * @return true if the genre exists, false otherwise
     */
    public boolean doesGenreExist(String type) {
        return genreRepository.findByType(type).isPresent();
    }

    public boolean doesGenreExistManyGenres(List<Long> genreIds) {
        // Check if all genre IDs exist in the database
        List<Genre> genres = genreRepository.findAllById(genreIds);

        // If the number of genres retrieved matches the number of genre IDs, then all genres exist
        return genres.size() == genreIds.size();
    }

    /**
     * Updates an existing genre in the database.
     *
     * @param id           the ID of the genre to update.
     * @param genreDetails the new details for the genre to be updated.
     * @return the updated genre object.
     * @throws ResourceNotFoundException if the genre with the specified ID is not found.
     * @throws IllegalArgumentException if a genre with the new type already exists.
     */
    public Genre updateGenre(Long id, Genre genreDetails) {
        // Fetch the genre from the repository by its ID. If not found, throw ResourceNotFoundException
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre does not exist with id: " + id));

        // Check if the new type is different and already exists in the database, throw IllegalArgumentException if true
        if (!existingGenre.getType().equalsIgnoreCase(genreDetails.getType()) && doesGenreExist(genreDetails.getType())) {
            throw new IllegalArgumentException("Genre already exists");
        }

        // Update the genre's type to the new type provided in genreDetails
        existingGenre.setType(genreDetails.getType());

        // Save the updated genre back to the repository and return it
        return genreRepository.save(existingGenre);
    }

    /**
     * Deletes a genre from the database by its ID.
     *
     * @param id the ID of the genre to delete.
     * @return a map indicating whether the genre was successfully deleted.
     * @throws ResourceNotFoundException if the genre with the given ID is not found.
     * @throws IllegalArgumentException if the genre is associated with one or more books and cannot be deleted.
     */
    public Map<String, Boolean> deleteGenre(Long id) {
        // Retrieve the genre from the repository by its ID. If not found, throw ResourceNotFoundException
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + id));

        // Check if the genre is associated with any books
        List<Book> booksWithGenre = bookRepository.findByGenreId(id);
        if (!booksWithGenre.isEmpty()) {
            throw new IllegalArgumentException("Genre is associated with one or more books and cannot be deleted.");
        }

        // Delete the genre if it is not associated with any books
        genreRepository.delete(genre);

        // Return a simple response indicating the genre was deleted
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
