package com.api.backincdidents.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.api.backincdidents.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    
    public List<Notification> findByAssigne_IdEqualsOrDeclarant_IdEqualsOrAdmin_IdEquals(int assigneId, int declarantId, int adminId);


}
