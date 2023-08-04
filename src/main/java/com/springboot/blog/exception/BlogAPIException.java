package com.springboot.blog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.management.RuntimeMBeanException;
@AllArgsConstructor
@Getter
public class BlogAPIException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}
