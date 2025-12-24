package com.edu.Institiute.repo;


import com.edu.Institiute.entity.MedicalHistory;
import com.edu.Institiute.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepo extends JpaRepository<Notification,Integer> {

    @Query(value = "SELECT * FROM notification WHERE id=:notificationId", nativeQuery = true)
    Notification getAllNotificationForProvidedId(@Param("notificationId") String notificationId);

    @Query(value = "SELECT * FROM notification WHERE id=:notificationId", nativeQuery = true)
    List<Notification> getNotificationDetailById(@Param("notificationId") String notificationId);

    @Query(value = "SELECT * FROM notification WHERE id=:notificationId", nativeQuery = true)
    Optional<Notification> getNotificationById(@Param("notificationId") String notificationId);


}
