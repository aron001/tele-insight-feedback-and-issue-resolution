package com.gebeya.notificationservice.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    public void sendEmail(String emailTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}