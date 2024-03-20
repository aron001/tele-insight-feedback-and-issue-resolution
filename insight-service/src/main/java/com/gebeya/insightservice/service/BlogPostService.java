package com.gebeya.insightservice.service;

import com.gebeya.insightservice.dto.BlogPostResponseDto;
import com.gebeya.insightservice.dto.request.AddBlogRequestDto;
import com.gebeya.insightservice.model.BlogPost;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BlogPostService {

    ResponseEntity<BlogPost> add(AddBlogRequestDto blogPost);
    Optional<BlogPost> getById(int id);
    ResponseEntity<BlogPostResponseDto> getBlogPostById(int blogId);
    ResponseEntity<List<BlogPost>> searchPost(String keyword);
    ResponseEntity<List<BlogPost>> getAll();
    ResponseEntity<BlogPost> update(AddBlogRequestDto blogPost, int id);
    boolean delete(int id);
    BlogPost incrementLike(int id);
    BlogPost decrementLike(int id);
    BlogPost incrementDislike(int id);
    BlogPost decrementDislike(int id);

}
