package com.gebeya.insightservice.service.impl;

import com.gebeya.insightservice.dto.BlogPostResponseDto;
import com.gebeya.insightservice.dto.request.AddBlogRequestDto;
import com.gebeya.insightservice.model.BlogPost;
import com.gebeya.insightservice.model.Comment;
import com.gebeya.insightservice.repository.BlogPostRepository;
import com.gebeya.insightservice.repository.CommentRepository;
import com.gebeya.insightservice.service.BlogPostService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<BlogPost> add(AddBlogRequestDto blogPostDto) {
        if (blogPostDto.getContent() == null && blogPostDto.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title or Content is empty");
        }
        BlogPost newBlog = new BlogPost();
        newBlog.setTitle(blogPostDto.getTitle());
        newBlog.setContent(blogPostDto.getContent());
        newBlog.setIs_active(true);
        newBlog.setCreated_By("Admin"); // Will implement in authentication
        newBlog.setCreated_date(new Date().toInstant());
        // will add who created this blog.
        return new ResponseEntity<>(save(newBlog), HttpStatus.CREATED);
    }
    public ResponseEntity<BlogPostResponseDto> getBlogPostById(int blogId){
        Optional<BlogPost> optionalBlogPost = blogPostRepository.findById((long) blogId);
        if(optionalBlogPost.isPresent()){
            BlogPost post = optionalBlogPost.get();
            BlogPostResponseDto dto = new BlogPostResponseDto();
            dto.setCommentList(getCommentsByBlogPostId(blogId));
            dto.setDislikes(post.getDislikes());
            dto.setTitle(post.getTitle());
            dto.setLikes(post.getLikes());
            dto.setContent(post.getContent());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog Id is not found");
    }
    public ResponseEntity<List<BlogPost>> searchPost(String keyword) {
        return new ResponseEntity<>(blogPostRepository.searchPosts(keyword), HttpStatus.OK);
    }

    public ResponseEntity<List<BlogPost>> getAll() {
        return new ResponseEntity<>(blogPostRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<BlogPost> update(AddBlogRequestDto blogRequestDto, int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog Post Id is not found");
        }
        Optional<BlogPost> optionalBlogPost = blogPostRepository.findById((long) id);
        if(optionalBlogPost.isPresent()) {
            BlogPost existedPost = optionalBlogPost.get();
            if (blogRequestDto.getTitle() != null) {
                existedPost.setTitle(blogRequestDto.getTitle());
            }
            if (blogRequestDto.getContent() != null) {
                existedPost.setContent(blogRequestDto.getContent());
            }
            if (blogRequestDto.getIs_active() != null) {
                existedPost.setIs_active(blogRequestDto.getIs_active());
            }
            return new ResponseEntity<>(save(existedPost), HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Blog Post Id is not found");
    }
    public BlogPost updateLikeDislike(BlogPost blogPost) {

        Optional<BlogPost> optionalBlogPost = blogPostRepository.findById((long) blogPost.getId());
        if(optionalBlogPost.isPresent()) {

            BlogPost existedPost = optionalBlogPost.get();
            if (blogPost.getDislikes() != 0) {
                existedPost.setDislikes(blogPost.getDislikes());
            }
            if (blogPost.getLikes() != 0) {
                existedPost.setLikes(blogPost.getLikes());
            }
            return save(existedPost);
        }
        throw new RuntimeException("Blog Post Id is not found");
    }
    public Optional<BlogPost> getById(int id){
        if(isIdExisted(id)){
            throw new RuntimeException("Blog Id is not found");
        }
        return blogPostRepository.findById((long)id);
    }

    public boolean delete(int id) {
        if (isIdExisted(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post Id is not found");
        }
        blogPostRepository.deleteById((long) id);
        return true;
    }
    public BlogPost incrementLike(int id){
        Optional<BlogPost> existedBlogPost = blogPostRepository.findById((long)id);
        if(existedBlogPost.isPresent()){
            BlogPost post = existedBlogPost.get();
           int likes =  post.getLikes();
           post.setLikes(likes + 1);
           return updateLikeDislike(post);
        }
        return null;
    }
    public BlogPost incrementDislike(int id){
        Optional<BlogPost> existedBlogPost = blogPostRepository.findById((long)id);
        if(existedBlogPost.isPresent()){
            BlogPost post = existedBlogPost.get();
            int dislikes =  post.getDislikes();
            post.setDislikes(dislikes + 1);
            return updateLikeDislike(post);
        }
        return null;
    }
    public BlogPost decrementLike(int id){
        Optional<BlogPost> existedBlogPost = blogPostRepository.findById((long)id);
        if(existedBlogPost.isPresent()){
            BlogPost post = existedBlogPost.get();
            int likes =  post.getLikes();
            post.setLikes(likes - 1);
            return updateLikeDislike(post);
        }
        return null;
    }
    public BlogPost decrementDislike(int id){
        Optional<BlogPost> existedBlogPost = blogPostRepository.findById((long)id);
        if(existedBlogPost.isPresent()){
            BlogPost post = existedBlogPost.get();
            int dislikes =  post.getDislikes();
            post.setDislikes(dislikes - 1);
            return updateLikeDislike(post);
        }
        return null;
    }

    private boolean isIdExisted(int id) {
        return !blogPostRepository.existsById((long) id);
    }

    private BlogPost save(BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }
    private List<Comment> getCommentsByBlogPostId(int blogPostId){
        List<Comment> commentList = commentRepository.findAll();
        List<Comment> blogPostList = new ArrayList<>();
        for(Comment comment: commentList){
            if(comment.getBlogPost().getId() == blogPostId){
                blogPostList.add(comment);
            }
        }
        return blogPostList;
    }

}
