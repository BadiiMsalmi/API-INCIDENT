package com.api.backincdidents.controller;

import javax.mail.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
   
    @Autowired
    SimpMessagingTemplate simpMessaingTemplate;

    @MessageMapping("/application")
    @SendTo("/app/messages")
    public Message send(final Message message){
        return message;
    }


    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload Message message){
        // to code
    }
}
