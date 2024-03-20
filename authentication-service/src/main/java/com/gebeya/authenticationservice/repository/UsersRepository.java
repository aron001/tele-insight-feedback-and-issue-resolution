package com.gebeya.authenticationservice.repository;

import com.gebeya.authenticationservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserName(String username);
    Optional<Users> findFirstByUserName(String userName);
}
