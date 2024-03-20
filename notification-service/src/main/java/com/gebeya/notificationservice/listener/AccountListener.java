package com.gebeya.notificationservice.listener;

import com.gebeya.notificationservice.dto.requestDto.AccountDto;
import com.gebeya.notificationservice.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountListener {
    private final EmailService emailService;

    @KafkaListener(topics = {"user-welcome-notifications"}, containerFactory = "accountListenerFactory")
    void handleNewAccountEvent(AccountDto accountDto) {
        String message = "Hello, " + accountDto.getName() + "\n" +
                "Welcome to TeleInsight. " +
                "We are excited to have you on board." + "\n" +
                "Your username is: " + accountDto.getUserName() + "\n" +
                "Your password is: " + accountDto.getPassword();
        emailService.sendEmail(accountDto.getEmail(), "Welcome to Teleinsight", message);
    }
}
