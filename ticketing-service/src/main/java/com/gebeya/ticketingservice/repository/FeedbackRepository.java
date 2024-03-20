package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
