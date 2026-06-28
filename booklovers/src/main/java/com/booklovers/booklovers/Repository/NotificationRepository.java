package com.booklovers.booklovers.Repository;


import java.util.List;

 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booklovers.booklovers.Entity.Notification;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification> findByClubIdAndDeletedFalseOrderByCreatedAtDesc(Long clubId);

}