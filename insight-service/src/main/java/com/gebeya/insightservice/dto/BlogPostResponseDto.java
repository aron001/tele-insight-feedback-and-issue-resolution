package com.gebeya.insightservice.dto;

import com.gebeya.insightservice.model.Comment;
import lombok.Data;

import java.util.List;

@Data
public class BlogPostResponseDto {
    private String title;
    private String content;
    private int likes;
    private int dislikes;
    private List<Comment> commentList;
}
