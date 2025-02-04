package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Book;
import com.kaytmazvolkan.api.model.Transaction;
import com.kaytmazvolkan.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for Transaction entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Checks if a transaction exists for the given book and user
    boolean existsByBookAndUser(Book book, User user);

    // Retrieves the transaction associated with the given book and user
    Transaction findByBookAndUser(Book book, User user);
}
