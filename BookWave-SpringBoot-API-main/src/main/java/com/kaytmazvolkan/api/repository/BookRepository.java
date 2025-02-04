package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Book entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Retrieves a list of books associated with a specific genre by its ID
    @Query("SELECT b FROM Book b JOIN b.genres g WHERE g.id = :genreId")
    List<Book> findByGenreId(@Param("genreId") Long genreId);

    // Retrieves a list of books written by a specific author using their author ID
    @Query("SELECT b FROM Book b WHERE b.author.author_id = :authorId")
    List<Book> findByAuthorId(@Param("authorId") Long authorId);

    // Retrieves a book by its ISBN
    @Query("SELECT b FROM Book b WHERE b.isbn = :isbn")
    Book findByIsbn(@Param("isbn") String isbn);


}
