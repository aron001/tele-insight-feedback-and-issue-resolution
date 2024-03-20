package com.gebeya.insightservice.controller;

import com.gebeya.insightservice.service.LikeDislikeService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/api/v1/blogPosts/{blogPostId}")
public class LikeDislikeController {

    private final LikeDislikeService likeDislikeService;

    @PostMapping("/like")
    public ResponseEntity<Boolean> likeBlogPost(@PathVariable int blogPostId){
        return ResponseEntity.ok(likeDislikeService.likePost(blogPostId));
    }
    @PostMapping("/dislike")
    public ResponseEntity<Boolean> dislikePost(@PathVariable int blogPostId){
        return ResponseEntity.ok(likeDislikeService.dislikePost(blogPostId));
    }

    @DeleteMapping("/like")
    public ResponseEntity<Boolean> undoLikeBlogPost(@PathVariable int blogPostId){
        return ResponseEntity.ok(likeDislikeService.undoLikePost(blogPostId));
    }

    @DeleteMapping("/dislike")
    public ResponseEntity<Boolean> undoDislikePost(@PathVariable int blogPostId){
        return ResponseEntity.ok(likeDislikeService.undoDislikePost(blogPostId));
    }

}
