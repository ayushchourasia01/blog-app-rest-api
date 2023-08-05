package com.springboot.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private String name;
    private String username;
    private String email;
    private String password;
}
