package com.gebeya.insightservice.controller;

import com.gebeya.insightservice.dto.BlogPostResponseDto;
import com.gebeya.insightservice.dto.request.AddBlogRequestDto;
import com.gebeya.insightservice.model.BlogPost;
import com.gebeya.insightservice.service.BlogPostService;
import com.gebeya.insightservice.service.CommentService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/blog")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private final CommentService commentService;

    @GetMapping("/get")
    public ResponseEntity<List<BlogPost>> getAll(){

        return blogPostService.getAll();
    }
    @GetMapping("/search")
    public ResponseEntity<List<BlogPost>> search(@RequestParam String search){
        return blogPostService.searchPost(search);
    }
    @GetMapping("/{id}")
    public ResponseEntity<BlogPostResponseDto> getById(@PathVariable(value = "id") int postBlogId){
        return blogPostService.getBlogPostById(postBlogId);
    }
    @PostMapping
    public ResponseEntity<BlogPost> add(@RequestBody AddBlogRequestDto dto){
        return blogPostService.add(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> update(@RequestBody AddBlogRequestDto blogPost, @PathVariable(value = "id") int id ){
        return blogPostService.update(blogPost,id);
    }
    @DeleteMapping("/{id}")
    public boolean delete (@PathVariable(value = "id") int id){
        return blogPostService.delete(id);
    }
}
