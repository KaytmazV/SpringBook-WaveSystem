package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Book;
import com.kaytmazvolkan.api.model.Genre;
import com.kaytmazvolkan.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling business logic related to Books.
 * Provides CRUD operations for managing books in the system.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    /**
     * Constructor for BookService.
     * Initializes the dependencies required for book management.
     *
     * @param bookRepository the repository used to interact with the Book database.
     * @param authorService  the service responsible for interacting with authors.
     * @param genreService   the service responsible for managing genres.
     */
    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    /**
     * Retrieves all books from the database.
     *
     * @return a list of all books.
     */
    public List<Book> getAllBooks() {
        // Retrieves a list of all books from the database
        return bookRepository.findAll();
    }

    /**
     * Retrieves a specific book by its ID.]
     *
     * @param id the ID of the book to retrieve.
     * @return the found book.
     * @throws ResourceNotFoundException if the book is not found.
     */
    public Book getBookById(Long id) {
        // Fetch the book entity by ID or throw an exception if not found
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    /**
     * Checks if a book exists in the database based on its ISBN.
     *
     * @param book the book to check.
     * @return true if the book exists, false otherwise.
     */
    public boolean doesBookExist(Book book) {
        // Check if the book is null or has an invalid ID
        if (book == null || book.getISBN() == null) {
            return false;
        }

        // If a book is found by isbn, it returns true, otherwise false
        return bookRepository.findByIsbn(book.getISBN()) != null;
    }

    /**
     * Creates a new book in the database.
     * Checks if the book already exists, if the author exists, and if the genres are valid.
     *
     * @param book the book to create.
     * @return the created book.
     * @throws ResourceNotFoundException if the author or genres do not exist.
     */
    public Book createBook(Book book) {
        // Check if the book already exists in the database using the ISBN
        if ((doesBookExist(book))) {
            throw new ResourceNotFoundException("Book already exist");
        }

        // Check if the book's author exists
        if (authorService.getAuthorById(book.getAuthor().getAuthor_id()) == null) {
            throw new ResourceNotFoundException("Author does not exist");
        }

        // Initialize a list to store valid genres
        List<Genre> validGenres = new ArrayList<>();

        // Iterate through each genre provided in the book's genre list
        for (int i = 0; i < book.getGenres().size(); i++) {
            Genre genre = book.getGenres().get(i);

            // Check if each genre exists in the database by its ID
            Genre fetchedGenre = genreService.getGenreById(genre.getGenreId());

            if (fetchedGenre == null) {
                // If any genre is invalid, throw an exception with the genre's ID
                throw new ResourceNotFoundException("Genre does not exist with id: " + genre.getGenreId());
            }

            // If the genre is valid, add it to the validGenres list
            validGenres.add(fetchedGenre);
        }

        // Set the valid genres to the book
        book.setGenres(validGenres);

        // Set the author correctly
        book.setAuthor(authorService.getAuthorById(book.getAuthor().getAuthor_id()));

        // Save the book if all checks pass
        return bookRepository.save(book);
    }

    /**
     * Updates an existing book in the repository.
     *
     * @param id          the ID of the book to be updated.
     * @param bookDetails the new details of the book to be updated.
     * @return the updated book.
     * @throws ResourceNotFoundException if the book, author, or genre does not exist.
     * @throws IllegalArgumentException  if the ISBN already exists in the system.
     */
    public Book updateBook(Long id, Book bookDetails) {
        // Retrieve the existing book by its ID.
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book does not exist with id: " + id));

        // Validate if the ISBN of the existing book matches the provided one to avoid duplicates.
        if (!existingBook.getISBN().equalsIgnoreCase(bookDetails.getISBN())) {
            throw new IllegalArgumentException("Book already exists with ISBN: " + bookDetails.getISBN());
        }

        // Check if the author of the book exists.
        if (authorService.getAuthorById(bookDetails.getAuthor().getAuthor_id()) == null) {
            throw new ResourceNotFoundException("Author does not exist");
        }

        // Validate that each genre exists.
        for (int i = 0; i < bookDetails.getGenres().size(); i++) {
            Genre genre = bookDetails.getGenres().get(i);

            // Fetch the genre by its ID to check if it exists.
            Genre fetchedGenre = genreService.getGenreById(genre.getGenreId());
            if (fetchedGenre == null) {
                throw new ResourceNotFoundException("Genre does not exist with id: " + genre.getGenreId());
            }

            // Update the existing book with the new details provided in the 'bookDetails' parameter.
            existingBook.setUpdateBook(bookDetails.getTitle(), bookDetails.getPublisher(), bookDetails.getPublished_year(),
                    bookDetails.getStatus(), bookDetails.getDescription(), bookDetails.getAuthor(), bookDetails.getQuantity(),
                    bookDetails.getRate(), bookDetails.getGenres(), bookDetails.getImage());


        }
        // Save the updated book back into the repository.
        return bookRepository.save(existingBook);
    }

    /**
     * Deletes a book by its ID.\
     *
     * @param id the ID of the book to delete.
     * @return a response indicating if the book was deleted.
     * @throws ResourceNotFoundException if the book is not found.
     */
    public Map<String, Boolean> deleteBook(Long id) {
        // Find the book in the database by its ID
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        // If the book is found, delete it from the repository
        bookRepository.delete(book);

        // Return a simple response indicating the author was deleted
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;  // Return the response
    }
}


