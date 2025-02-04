package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Image entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface ImageRepository extends JpaRepository<Author, Long> {


}
