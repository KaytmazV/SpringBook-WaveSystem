package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Author entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    // Finds an author by their first and last name, returning an Optional<Author>.
    @Query("SELECT a FROM Author a WHERE a.first_name = :firstName AND a.last_name = :lastName")
    Optional<Author> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
