package com.gebeya.insightservice.dto.request;

import lombok.Data;

@Data
public class AddBlogRequestDto {
    private String title;
    private String content;
    private Boolean is_active;
}
