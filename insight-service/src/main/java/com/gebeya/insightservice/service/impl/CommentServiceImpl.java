package com.gebeya.insightservice.service.impl;

import com.gebeya.insightservice.model.BlogPost;
import com.gebeya.insightservice.model.Comment;
import com.gebeya.insightservice.repository.CommentRepository;
import com.gebeya.insightservice.service.BlogPostService;
import com.gebeya.insightservice.service.CommentService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BlogPostService blogPostService;

    public Comment addComment(int postId, Comment comment){
        Optional<BlogPost> blogPost = blogPostService.getById(postId);
        if(blogPost.isPresent()){
            BlogPost post = blogPost.get();
            comment.setBlogPost(post);
            return commentRepository.save(comment);
        }else{
            throw new IllegalArgumentException("Post not found");
        }
    }
    public List<Comment> getCommentsByBlogPostId(int blogPostId){
        List<Comment> commentList = commentRepository.findAll();
        List<Comment> blogPostList = new ArrayList<>();
        for(Comment comment: commentList){
            if(comment.getBlogPost().getId() == blogPostId){
                blogPostList.add(comment);
            }
        }
        return blogPostList;
    }
    public boolean delete(int commentId){
        Optional<Comment> comment = commentRepository.findById((long) commentId);
        if(comment.isPresent()){
            Comment existedComment = comment.get();
            commentRepository.delete(existedComment);
        }else{
            throw new IllegalArgumentException("Comment not found");
        }
        return true;
    }

}
