package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Genre entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    // Finds a Genre by its type.
    Optional<Genre> findByType(String type);
}
