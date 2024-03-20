package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByRoleId(Integer id);
}
