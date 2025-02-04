package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.DTO.NotificationCreateDTO;
import com.kaytmazvolkan.api.DTO.TransactionDTO;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Book;
import com.kaytmazvolkan.api.model.Transaction;
import com.kaytmazvolkan.api.model.User;
import com.kaytmazvolkan.api.repository.BookRepository;
import com.kaytmazvolkan.api.repository.TransactionRepository;
import com.kaytmazvolkan.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to transactions.
 * Provides CRUD operations for managing transactions in the system.
 */
@Service
public class TransactionService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService;

    /**
     * Constructor for the TransactionService class.
     *
     * @param transactionRepository the repository for accessing transaction data.
     * @param bookRepository        the repository for accessing book data.
     * @param userRepository        the repository for accessing user data.
     */
    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              BookRepository bookRepository, UserRepository userRepository,
                              NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    /**
     * Retrieve all transactions from the database.
     *
     * @return List of Transaction objects.
     */
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(TransactionDTO::new) // Convert each Transaction to TransactionDTO
                .collect(Collectors.toList());
    }

    /**
     * Get a specific transaction by its ID.
     * If the transaction is not found, a ResourceNotFoundException is thrown.
     *
     * @param id the ID of the transaction to retrieve.
     * @return the found Transaction object.
     * @throws ResourceNotFoundException if no transaction is found with the given ID.
     */
    public TransactionDTO getTransactionById(Long id) {
        // Fetch the transaction entity by ID or throw an exception if not found
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        // Convert the Transaction entity to TransactionDTO
        return new TransactionDTO(transaction);
    }

    /**
     * Handles the logic for borrowing a book.
     * Checks if the book is available, if the user is eligible to borrow it,
     * and then creates a transaction to record the borrowing activity.
     *
     * @param bookId   The ID of the book to be borrowed.
     * @param username The username of the user borrowing the book.
     * @throws ResourceNotFoundException if the book or user is not found in the database.
     * @throws IllegalStateException     if the book is not available for borrowing,
     *                                   if the user has already borrowed the book, or if the book is out of stock.
     */
    public void borrowBook(Long bookId, String username) {
        try {
            // Check if the book exists
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

            // Check if the user exists
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

            // Check if the book is available for borrowing
            if (!"AVAILABLE".equalsIgnoreCase(book.getStatus())) {
                throw new IllegalStateException("The book is not available for borrowing.");
            }

            // Ensure the book has quantity > 0 to borrow
            if (book.getQuantity() <= 0) {
                throw new IllegalStateException("The book is out of stock.");
            }

            // Check if the user has already borrowed this book
            boolean alreadyBorrowed = transactionRepository.existsByBookAndUser(book, user);
            if (alreadyBorrowed) {
                throw new IllegalStateException("You have already borrowed this book.");
            }

            // Create a new transaction for borrowing the book
            Transaction transaction = new Transaction();
            transaction.setBook(book);
            transaction.setUser(user);
            transaction.setIssueDate(LocalDate.now());
            transaction.setDueDate(LocalDate.now().plusMonths(1));

            // Save the transaction
            transactionRepository.save(transaction);

            // Update the book's quantity and status after borrowing
            book.setQuantity(book.getQuantity() - 1); // Decrease the available stock by 1

            // If quantity becomes 0, mark the book as unavailable
            if (book.getQuantity() == 0) {
                book.setStatus("UNAVAILABLE");
            }

            // Create a borrowing notification (without fine for now)
            NotificationCreateDTO notificationCreateDTO = new NotificationCreateDTO(bookId, username);
            notificationService.createNotificationBorrow(notificationCreateDTO);

            // Save the updated book record
            bookRepository.save(book);

        } catch (Exception e) {
            e.printStackTrace(); // Log stack trace for debugging
            throw new RuntimeException("Error occurred while borrowing the book: " + e.getMessage());
        }
    }

    /**
     * Handles the logic for returning a borrowed book.
     * Checks if the book and user exist, ensures the user has borrowed the book,
     * and updates the book's quantity and status after the return.
     *
     * @param bookId   The ID of the book being returned.
     * @param username The username of the user returning the book.
     * @throws ResourceNotFoundException if the book or user is not found in the database.
     * @throws IllegalStateException     if the user has not borrowed the book, or if the book has already been returned.
     */
    public void returnBook(Long bookId, String username) {
        // Check if the book exists
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + bookId));

        // Check if the user exists
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        // Check if the user has borrowed this book
        Transaction transaction = transactionRepository.findByBookAndUser(book, user);
        if (transaction == null) {
            throw new IllegalStateException("The user has not borrowed this book.");
        }

        // Check if the book has been returned before
        if (transaction.getReturnDate() != null) {
            throw new IllegalStateException("The user has already returned book.");
        }

        // Update the transaction to reflect the return
        transaction.setReturnDate(LocalDate.now()); // Set the return date to the current time

        // Update the book's status and quantity
        book.setStatus("AVAILABLE");
        book.setQuantity(book.getQuantity() + 1);

        // Save the updated transaction and book
        transactionRepository.save(transaction);
        bookRepository.save(book);
    }

    /**
     * Deletes a transaction based on its ID.
     * If the transaction exists, it is removed, and a response indicating success is returned.
     *
     * @param id The ID of the transaction to be deleted.
     * @return A map containing a key "deleted" with a value of `true` indicating that the transaction has been successfully deleted.
     * @throws ResourceNotFoundException if the transaction with the given ID does not exist.
     */
    public Map<String, Boolean> deleteTransaction(Long id) {
        // Find the transaction by its ID
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));

        // Delete the found transaction
        transactionRepository.delete(transaction);

        // Return a simple response indicating the transaction was deleted
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;  // Return the response
    }
}
