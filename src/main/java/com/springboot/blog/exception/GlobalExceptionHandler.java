package com.springboot.blog.exception;

import com.springboot.blog.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFound(ResourceNotFoundException exception, ServletWebRequest webRequest){
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                webRequest.getRequest().getRequestURI()

        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDTO> handleBlogAPIException(BlogAPIException exception, ServletWebRequest webRequest){
        ErrorDTO errorDTO = new ErrorDTO(
                new Date(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                webRequest.getRequest().getRequestURI()

        );
        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String,String> listOfErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)-> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            listOfErrors.put(fieldName,message);
        });
        return new ResponseEntity<>(listOfErrors,HttpStatus.BAD_REQUEST);
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
