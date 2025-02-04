package com.kaytmazvolkan.api.repository;

import com.kaytmazvolkan.api.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Notification entity, extending JpaRepository for CRUD operations.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
