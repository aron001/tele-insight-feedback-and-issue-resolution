package com.gebeya.insightservice.model;

import jakarta.persistence.Entity;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BlogPost extends BaseModel{
    private String title;
    private String content;
    private int likes;
    private int dislikes;
}
