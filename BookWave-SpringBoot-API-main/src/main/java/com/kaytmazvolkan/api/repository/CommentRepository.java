package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Comment entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
