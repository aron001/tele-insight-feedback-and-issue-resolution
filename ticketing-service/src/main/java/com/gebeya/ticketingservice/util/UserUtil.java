package com.gebeya.ticketingservice.util;

import com.gebeya.ticketingservice.dto.requestDto.AddAccountRequestDto;
import com.gebeya.ticketingservice.enums.Authority;
import com.gebeya.ticketingservice.model.Users;
import com.gebeya.ticketingservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class UserUtil {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, AddAccountRequestDto> kafkaTemplate;

    public void createUser(String fName, String mName, Integer id, Authority authority, String email) {
        String username = fName.toLowerCase() + "." + mName.toLowerCase().charAt(0) + getRandomNumber();
        String password = String.valueOf(randomStringGenerator());
        Users users = Users.builder()
                .userName(username)
                .password(passwordEncoder.encode(password))
                .authority(authority)
                .is_active(true)
                .roleId(id)
                .build();
        Users user = usersRepository.save(users);
        AddAccountRequestDto request = AddAccountRequestDto.builder().name(fName).email(email).userName(username)
                .password(password).build();
        System.out.println(password);
        kafkaTemplate.send("user-welcome-notifications", request);
    }

    private static int getRandomNumber() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    private static StringBuilder randomStringGenerator() {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        final int DEFAULT_LENGTH = 12;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < DEFAULT_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(index);
            stringBuilder.append(randomChar);
        }
        return stringBuilder;
    }

    public void userDisabler(Users users) {

        users.setIs_active(false);
        usersRepository.save(users);
    }
}
