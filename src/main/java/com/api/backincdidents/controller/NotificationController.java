package com.api.backincdidents.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.backincdidents.model.Notification;
import com.api.backincdidents.service.NotificationService;

@RestController
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    public List<Notification> getNotification(@RequestParam int id){
        return notificationService.getNotifications(id);
    }
}
