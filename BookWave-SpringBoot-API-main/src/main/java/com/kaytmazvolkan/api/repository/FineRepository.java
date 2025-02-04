package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Fine;
import com.kaytmazvolkan.api.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Fine entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {

    // Find fines by associated transaction
    Optional<Fine> findByTransaction(Transaction transaction);
}
