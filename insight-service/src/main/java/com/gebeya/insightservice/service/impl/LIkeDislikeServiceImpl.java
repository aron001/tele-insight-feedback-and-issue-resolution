package com.gebeya.insightservice.service.impl;

import com.gebeya.insightservice.model.BlogPost;
import com.gebeya.insightservice.model.Dislike;
import com.gebeya.insightservice.model.Likes;
import com.gebeya.insightservice.repository.BlogPostRepository;
import com.gebeya.insightservice.repository.DislikeRepository;
import com.gebeya.insightservice.repository.LikeRepository;
import com.gebeya.insightservice.service.BlogPostService;
import com.gebeya.insightservice.service.LikeDislikeService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class LIkeDislikeServiceImpl implements LikeDislikeService {

    private final LikeRepository likeRepository;
    private final DislikeRepository dislikeRepository;
    private final BlogPostRepository blogPostRepository;
    private final BlogPostService blogPostService;

    public boolean likePost(int blogPostId){
        Optional<BlogPost> blogPost = blogPostService.getById(blogPostId);
        if(blogPost.isPresent()){
            BlogPost post = blogPost.get();
            //user will change in authentication
            Likes like = new Likes(2,post);
            likeRepository.save(like);
            blogPostService.incrementLike(Math.toIntExact(post.getId()));
            return true;
        }
        else {
            throw new IllegalArgumentException("Blog Post not found");
        }
    }
    public boolean dislikePost(int blogPostId){
        Optional<BlogPost> blogPost = blogPostService.getById(blogPostId);
        if(blogPost.isPresent()){
            BlogPost post = blogPost.get();
            // user id will change in authentication
            Dislike dislike = new Dislike(2,post);
            dislikeRepository.save(dislike);
            blogPostService.incrementDislike(blogPostId);
            return true;
        }
        else {
            throw new IllegalArgumentException("Blog Post not found");
        }
    }
    public boolean undoLikePost(int blogPostId){
        Optional<BlogPost> blogPost = blogPostService.getById(blogPostId);
        if(blogPost.isPresent()) {
          //  BlogPost post = blogPost.get();
            List<Likes> likeList = likeRepository.findAll();
           // Likes like = new Likes();
            for (Likes like1 : likeList) {
                if (like1.getBlogPost().getId() == blogPostId) {
                    likeRepository.delete(like1);
                    blogPostService.decrementLike(blogPostId);
                    break;
                }
            }
            return true;
        }
        else {
            throw new IllegalArgumentException("Blog Post not found");
        }
    }
    public boolean undoDislikePost(int blogPostId){
        Optional<BlogPost> blogPost = blogPostService.getById(blogPostId);
        if(blogPost.isPresent()) {
           // BlogPost post = blogPost.get();
            List<Dislike> dislikes = dislikeRepository.findAll();
          //  Dislike dislike= new Dislike();
            for (Dislike dislike1 : dislikes) {
                if (dislike1.getBlogPost().getId() == blogPostId) {
                    dislikeRepository.delete(dislike1);
                    blogPostService.decrementDislike(blogPostId);
                    break;
                }
            }
            return true;
        }
        else {
            throw new IllegalArgumentException("Blog Post not found");
        }
    }

}
