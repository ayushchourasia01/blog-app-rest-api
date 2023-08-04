package com.springboot.blog.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long Id;
    private String emailId;
    private String name;
    private String body;
}
