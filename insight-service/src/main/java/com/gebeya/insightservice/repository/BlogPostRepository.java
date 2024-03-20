package com.gebeya.insightservice.repository;

import com.gebeya.insightservice.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    @Query("SELECT p FROM BlogPost p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<BlogPost> searchPosts(@Param("keyword") String keyword);
}
