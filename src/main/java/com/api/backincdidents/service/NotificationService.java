package com.api.backincdidents.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.backincdidents.model.Notification;
import com.api.backincdidents.repository.NotificationRepository;

@Service
public class NotificationService {


    @Autowired
    private NotificationRepository notificationRepository;

    public Notification saveNotification(Notification user){
        return this.notificationRepository.save(user);
    }

    public List<Notification> getNotifications(int id){
        return this.notificationRepository.findByAssigne_IdEqualsOrDeclarant_IdEqualsOrAdmin_IdEquals(id,id,id);
    }
}
