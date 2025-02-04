package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Query to find user by their username
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(String username);
}
