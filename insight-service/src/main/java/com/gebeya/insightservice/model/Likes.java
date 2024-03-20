package com.gebeya.insightservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Likes extends BaseModel{

    private int userId;
    @ManyToOne
    private BlogPost blogPost;
}
