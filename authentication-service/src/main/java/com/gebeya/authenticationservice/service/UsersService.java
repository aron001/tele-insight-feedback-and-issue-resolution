package com.gebeya.authenticationservice.service;

import com.gebeya.authenticationservice.model.Users;
import com.gebeya.authenticationservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository userRepository;

    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    public void createUser(Users user) {
        userRepository.save(user);
    }

    @Cacheable(value = "user", key = "#userName")
    public Users loadUserByUsername(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

}
