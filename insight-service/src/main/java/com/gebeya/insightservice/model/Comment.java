package com.gebeya.insightservice.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends BaseModel{

    private String content;
    private int user_id;
    @ManyToOne
    private BlogPost blogPost;
}
