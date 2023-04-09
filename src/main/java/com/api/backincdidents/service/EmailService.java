package com.api.backincdidents.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.api.backincdidents.model.RestorePasswordToken;
import com.api.backincdidents.model.User;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

    @Async
    public void sendForgetPasswordEmail(User user, RestorePasswordToken token) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Forget password!");
        mailMessage.setFrom("admibot69@outlook.fr");
        mailMessage.setText(
                "Hello, " + user.getFirstname() + "\nHere's your code to change your password: " + token.getToken());
        javaMailSender.send(mailMessage);
    }

}
