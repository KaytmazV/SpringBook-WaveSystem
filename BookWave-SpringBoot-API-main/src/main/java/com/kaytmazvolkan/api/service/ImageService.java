package com.kaytmazvolkan.api.service;

import com.kaytmazvolkan.api.model.Image;
import com.kaytmazvolkan.api.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Image-related operations.
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    /**
     * Constructor for ImageService.
     * Initializes the ImageRepository used to interact with the Image data source.
     *
     * @param imageRepository the repository to interact with Image data in the database.
     */
    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Checks if the specified Image exists in the repository based on its ID.
     *
     * @param image the image object to check for existence.
     * @return {@code true} if the image exists in the repository, {@code false} otherwise.
     */
    public boolean doesImageExist(Image image) {
        // Check if image is null or has an invalid ID
        if (image == null || image.getId() == 0) {
            return false;
        }
        // Check if the image exists in the repository
        return imageRepository.findById(image.getId()).isPresent();
    }
}
