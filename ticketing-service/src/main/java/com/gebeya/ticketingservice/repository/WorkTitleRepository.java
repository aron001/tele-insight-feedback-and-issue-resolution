package com.gebeya.ticketingservice.repository;

import com.gebeya.ticketingservice.model.WorkTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkTitleRepository extends JpaRepository<WorkTitle, Long> {
}
