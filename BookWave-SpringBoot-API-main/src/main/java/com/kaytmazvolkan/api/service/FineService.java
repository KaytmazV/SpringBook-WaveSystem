package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.DTO.FineDTO;
import com.kaytmazvolkan.api.DTO.NotificationCreateDTO;
import com.kaytmazvolkan.api.exception.AlreadyPaidException;
import com.kaytmazvolkan.api.exception.NoFineRequiredException;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.model.Fine;
import com.kaytmazvolkan.api.model.Transaction;
import com.kaytmazvolkan.api.repository.FineRepository;
import com.kaytmazvolkan.api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to Fines.
 * Provides CRUD operations for managing fines in the system.
 */
@Service
public class FineService {

    private final FineRepository fineRepository;
    private final TransactionRepository transactionRepository;
    private final NotificationService notificationService; // Lazy injection


    /**
     * Constructor for the FineService class.
     *
     * @param fineRepository the repository to interact with the Fine database for CRUD operations.
     * @param transactionRepository the repository to interact with the Transaction database for CRUD operations.
     * @param notificationService the service to handle notifications related to fines or transactions.
     */
    @Autowired
    public FineService(FineRepository fineRepository, TransactionRepository transactionRepository,
                       NotificationService notificationService) {
        this.fineRepository = fineRepository;
        this.transactionRepository = transactionRepository;
        this.notificationService = notificationService;
    }

    /**
     * Retrieves all fines from the database and returns them as a list of Data Transfer Objects (DTOs).
     *
     * @return A list of `FineDTO` objects, each representing a fine in the database.
     */
    public List<FineDTO> getAllFines() {
        // Retrieve all Fine entities from the repository
        List<Fine> fines = fineRepository.findAll();

        // Convert each Fine to FineDTO and return the list
        return fines.stream()
                .map(FineDTO::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a fine by its ID and returns a Data Transfer Object (DTO) representation.
     *
     * @param id The unique identifier of the fine to be retrieved.
     * @return A `FineDTO` representing the fine with the specified ID.
     * @throws ResourceNotFoundException if the fine with the specified ID does not exist.
     */
    public FineDTO getFineById(Long id) {
        // Fetch the Fine entity by ID or throw an exception if the fine is not found
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + id));

        // Convert the Fine entity to FineDTO
        // FineDTO is a Data Transfer Object (DTO) containing a simplified version of the Fine entity
        return new FineDTO(fine);
    }

    /**
     * Checks whether a fine exists in the database by its ID.
     *
     * @param fine The `Fine` object whose existence is to be checked.
     * @return `true` if the fine exists in the database, `false` otherwise.
     */
    public boolean doesFineExist(Fine fine) {
        // Check if the fine is null or the fine's ID is 0
        if (fine == null || fine.getId() == 0) {
            return false;
        }

        // Check if the fine with the given ID exists in the repository
        return fineRepository.findById(fine.getId()).isPresent();
    }

    /**
     * Creates a new fine for a transaction if the book has not been returned on time.
     *
     * @param transactionId The unique identifier of the transaction for which the fine is to be created.
     * @return A FineDTO representing the newly created fine.
     * @throws ResourceNotFoundException if the transaction with the provided ID does not exist.
     * @throws NoFineRequiredException if the book was returned before the due date, so no fine is required.
     */
    public FineDTO createFine(long transactionId) {
        // Retrieve the Transaction entity by transactionId
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + transactionId));

        // Retrieve the dates to check
        LocalDate dueDate = transaction.getDueDate();
        LocalDate returnDate = transaction.getReturnDate();

        // If the book has been returned before the due date, no fine is required
        if (returnDate != null && returnDate.isBefore(dueDate)) {
            throw new NoFineRequiredException("The book was returned before the due date. No fine required.");
        }

        // Create a new Fine with a default values
        Fine fine = new Fine(50, "Unpaid", transaction, null);

        // Save the fine to the repository
        fine = fineRepository.save(fine);

        // Create a borrowing notification (with fine)
        NotificationCreateDTO notificationCreateDTO = new NotificationCreateDTO(fine.getId(), fine.getTransaction().getBook().getBook_id(),
                fine.getTransaction().getUser().getUsername());
        notificationService.createNotificationFine(notificationCreateDTO);


        // Save the fine to the repository
        return new FineDTO(fine);
    }

    /**
     * Updates the fine by setting the current date and time as the paid date if the fine has not already been paid.
     *
     * @param fineId The unique identifier of the fine to be updated.
     * @return The updated FineDTO containing the details of the updated fine.
     * @throws ResourceNotFoundException if the fine with the provided fineId does not exist.
     * @throws AlreadyPaidException if the fine has already been paid.
     */
    public FineDTO updateFine(long fineId) {
        // Retrieve the Fine entity by fineId
        Fine fine = fineRepository.findById(fineId)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + fineId));

        // Retrieve the paid date from the Fine entity
        LocalDateTime paidDate = fine.getPaid_date();

        // If the fine has already been paid, throw an exception
        if (paidDate != null) {
            throw new AlreadyPaidException("The fine has already been paid.");
        }

        // Set the paid date to the current time
        fine.setPaid_date(LocalDateTime.now());

        // Save the updated Fine entity
        fineRepository.save(fine);

        // Map the updated entity back to the FineDTO and return it
        return new FineDTO(fine);
    }

    /**
     * Checks and attempts to create a fine for a given transaction ID.
     *
     * @param transactionId the ID of the transaction to check for fine creation.
     * @return {@code true} if the fine was successfully created, {@code false} otherwise.
     *
     * @throws ResourceNotFoundException if the transaction with the given ID does not exist.
     * @throws NoFineRequiredException if no fine is required for the given transaction.
     * @throws Exception if any other unexpected error occurs during fine creation.
     */
    public boolean checkFineByBook(Long transactionId) {
        try {
            // Attempt to create the fine
            createFine(transactionId);
            return true;  // Fine creation was successful
        } catch (ResourceNotFoundException e) {
            // Transaction not found
            return false;  // No fine created
        } catch (NoFineRequiredException e) {
            // No fine is required
            return false;  // No fine created
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            return false;  // No fine created due to an error
        }
    }

    /**
     * Deletes a fine by its ID from the database.
     *
     * @param id The unique ID of the fine to delete.
     * @return A map containing a key `"deleted"` with a value of `true` indicating the successful deletion of the fine.
     * @throws ResourceNotFoundException if the fine with the provided ID is not found.
     */
    public Map<String, Boolean> deleteFine(Long id) {
        // Fetch Fine by id
        Fine fine = fineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fine not found with id: " + id));

        // Delete the fine
        fineRepository.delete(fine);

        // Return a simple response indicating the fine was deleted
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;  // Return the response
    }
}

