package com.gebeya.insightservice.service;

public interface LikeDislikeService {
    boolean likePost(int blogPostId);
    public boolean dislikePost(int blogPostId);
    boolean undoLikePost(int blogPostId);
    boolean undoDislikePost(int blogPostId);
}
