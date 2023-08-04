package com.springboot.blog.exception;

import com.springboot.blog.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class, BlogAPIException.class})
    public ResponseEntity<ErrorDTO> handleResourceNotFound(Exception exception, ServletWebRequest webRequest){
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                webRequest.getRequest().getRequestURI()

        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalExceptions(Exception exception, ServletWebRequest webRequest){
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                webRequest.getRequest().getRequestURI()
        );
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
