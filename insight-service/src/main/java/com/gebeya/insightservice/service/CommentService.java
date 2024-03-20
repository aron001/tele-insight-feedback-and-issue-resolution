package com.gebeya.insightservice.service;

import com.gebeya.insightservice.model.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(int postId, Comment comment);
    List<Comment> getCommentsByBlogPostId(int blogPostId);
    boolean delete(int commentId);
}
