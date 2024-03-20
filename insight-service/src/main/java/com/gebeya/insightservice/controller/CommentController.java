package com.gebeya.insightservice.controller;

import com.gebeya.insightservice.model.Comment;
import com.gebeya.insightservice.service.CommentService;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{blogPostId}")
    public Comment addComment(@PathVariable(value = "blogPostId") int blogPostId, @RequestBody Comment comment){
        return commentService.addComment(blogPostId, comment);
    }

    @GetMapping("/{blogPostId}")
    public List<Comment>getBlogPostComments(@PathVariable(value = "blogPostId") int blogPostId ){
        return commentService.getCommentsByBlogPostId(blogPostId);
    }
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable(value = "id") int id){
        return commentService.delete(id);
    }
}
