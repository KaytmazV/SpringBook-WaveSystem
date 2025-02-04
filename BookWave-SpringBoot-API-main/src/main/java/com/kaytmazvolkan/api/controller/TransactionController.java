package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.DTO.TransactionDTO;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;

import com.kaytmazvolkan.api.model.BorrowRequest;
import com.kaytmazvolkan.api.model.ReturnRequest;
import com.kaytmazvolkan.api.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all transaction-related requests.
 */
@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    // Constructor to inject services
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Retrieve all transactions from the system.
     *
     * @return ResponseEntity with a list of TransactionDTOs.
     */
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        // Get all transactions using the service
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    /**
     * Retrieve a specific transaction by its ID.
     *
     * @param id The ID of the transaction to retrieve.
     * @return ResponseEntity with the TransactionDTO or error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        try {
            // Call the service to get the TransactionDTO
            TransactionDTO transactionDTO = transactionService.getTransactionById(id);
            return ResponseEntity.ok(transactionDTO);
        } catch (ResourceNotFoundException ex) {
            // Handle the case where the transaction is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Endpoint for borrowing a book.
     *
     * @param borrowRequest The request containing the book ID and username.
     * @return ResponseEntity with a success or error message.
     */
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestBody BorrowRequest borrowRequest) {
        try {
            // Call the service layer to borrow the book
            transactionService.borrowBook(borrowRequest.getBookId(), borrowRequest.getUsername());

            // Success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Book borrowed successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            // Handle the case where the book is not available or already borrowed
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (ResourceNotFoundException e) {
            // Handle cases where the book or user is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace(); // Log the stack trace
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Endpoint for returning a borrowed book.
     *
     * @param returnBookRequest The request containing the book ID and username.
     * @return ResponseEntity with a success or error message.
     */
    @PutMapping("/return")
    public ResponseEntity<?> returnBook(@RequestBody ReturnRequest returnBookRequest) {
        try {
            // Call the service layer to handle the return
            transactionService.returnBook(returnBookRequest.getBookId(), returnBookRequest.getUsername());

            // Success response
            Map<String, String> response = new HashMap<>();
            response.put("message", "Book returned successfully");
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (ResourceNotFoundException e) {
            // Handle case where book or user is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalStateException e) {
            // Handle case where user has not borrowed the book or any other state error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            // Handle any unexpected errors
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Deletes a transaction based on its ID.
     *
     * @param id The ID of the transaction to be deleted.
     * @return ResponseEntity with a success or error message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTransaction(@PathVariable Long id) {
        try {
            // Call service layer to delete the transaction by its ID
            Map<String, Boolean> result = transactionService.deleteTransaction(id);
            Map<String, Object> response = new HashMap<>(result);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            // Handle the case where the transaction is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (IllegalArgumentException e) {
            // Handle invalid arguments
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}


