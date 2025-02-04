package com.kaytmazvolkan.api.controller;

import com.kaytmazvolkan.api.DTO.FineCreateRequestDTO;
import com.kaytmazvolkan.api.DTO.FineDTO;
import com.kaytmazvolkan.api.DTO.FineUpdateDTO;
import com.kaytmazvolkan.api.exception.AlreadyPaidException;
import com.kaytmazvolkan.api.exception.NoFineRequiredException;
import com.kaytmazvolkan.api.exception.ResourceNotFoundException;
import com.kaytmazvolkan.api.service.FineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller to handle all fine-related requests.
 */
@RestController
@RequestMapping("api/fines")
public class FineController {

    private final FineService fineService;

    // Constructor to inject services
    public FineController(FineService fineService) {
        this.fineService = fineService;
    }

    /**
     * Retrieves a list of all fines from the database.
     *
     * @return A list of `FineDTO` objects representing all fines in the system.
     */
    @GetMapping
    public List<FineDTO> getAllFines() {
        // Call the service method to fetch all fines and return the list of FineDTOs
        return fineService.getAllFines();
    }

    /**
     * Retrieves a fine by its ID.
     *
     * @param id The unique ID of the fine to retrieve.
     * @return A `ResponseEntity` containing either the fine details (FineDTO) or an error message.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getFineById(@PathVariable Long id) {
        try {
            // Call the service to get the FineDTO based on the fine ID
            FineDTO fineDTO = fineService.getFineById(id);

            // Return the fine details with a 200 OK status
            return ResponseEntity.ok(fineDTO);

        } catch (ResourceNotFoundException ex) {
            // Prepare an error response in case the fine with the given ID is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());

            // Return a 404 Not Found status with the error message
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Creates a fine for a transaction and returns the created fine as a response.
     *
     * @param fineCreateRequest The request body containing the transaction ID for which the fine is to be created.
     * @return A `ResponseEntity` containing the response status and either the created fine or error details.
     */
    @PostMapping
    public ResponseEntity<?> createFine(@RequestBody FineCreateRequestDTO fineCreateRequest) {
        // Extract the transaction ID from the request body
        long id = fineCreateRequest.getTransactionId();

        // Validate the transaction ID; it must be a positive integer
        if(id <= 0){
            // Prepare the error response for an invalid transaction ID
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid transaction id. Must be a positive integer.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            // Call the service to create the fine based on the transaction ID
            FineDTO createdFine = fineService.createFine(id);

            // Prepare the success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Fine created successfully");
            response.put("fine", createdFine);

            // Return the created fine with a 201 Created status
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (ResourceNotFoundException ex) {
            // Prepare an error response if the transaction id was not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (NoFineRequiredException e) {
            // Prepare an error response if no fine is required
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    /**
     * Updates the fine status by marking it as paid, and returns the updated fine as a response.
     *
     * @param fineUpdateRequest The request body containing the fine ID to be updated.
     * @return A `ResponseEntity` containing the response status and the updated fine or error details.
     */
    @PutMapping
    public ResponseEntity<?> updateFine(@RequestBody FineUpdateDTO fineUpdateRequest) {
        // Extract the fine ID from the request body
        long fineId = fineUpdateRequest.getFineId();

        // Validate the fine ID; must be a positive integer
        if(fineId <= 0) {
            // Prepare the error response for an invalid fine ID
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid fine id. Must be a positive integer.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
            // Call the service to update the fine based on the fine ID
            FineDTO updatedFine = fineService.updateFine(fineId);

            // Prepare the success response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Fine updated successfully");
            response.put("fine", updatedFine);

            // Return the updated fine with a 200 OK status
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            // Prepare the error response if the fine ID is not found
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (AlreadyPaidException e) {
            // Prepare the error response if the fine has already been paid
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    /**
     * Checks whether a fine exists or needs to be created for a particular transaction.
     *
     * @param transactionId the transaction ID for which the fine needs to be checked.
     * @return a {@link ResponseEntity} containing the status and message:
     *         - `201 Created` with a success message if a fine was created.
     *         - `200 OK` with a message indicating that no fine was created.
     *         - `500 Internal Server Error` with an error message in case of any exception.
     */
    @GetMapping("/check/{transactionId}")
    public ResponseEntity<?> checkFineByBook(@PathVariable Long transactionId) {
        try {
            // Call the service method to check or create the fine
            boolean isFineCreated = fineService.checkFineByBook(transactionId);

            if (isFineCreated) {
                // Fine was created successfully
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Fine created successfully for transaction ID: " + transactionId);
            } else {
                // No fine was created, so return a 200 OK with an appropriate message
                return ResponseEntity.status(HttpStatus.OK)
                        .body("No fine was created for transaction ID: " + transactionId);
            }
        } catch (Exception e) {
            // Prepare the error response if the fine has already been paid
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    /**
     * Deletes a fine by its ID from the system.
     *
     * @param id The unique ID of the fine to delete.
     * @return A `ResponseEntity` containing a map with either a success message or an error message
     * @throws ResourceNotFoundException if the fine with the provided ID is not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteFine(@PathVariable Long id) {
        try {
            // Attempt to delete the fine and return a response indicating success
            Map<String, Boolean> result = fineService.deleteFine(id);
            Map<String, Object> response = new HashMap<>(result);
            return ResponseEntity.ok(response);

        } catch (ResourceNotFoundException e) {
            // Handle case where fine is not found
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("deleted", Boolean.FALSE);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
