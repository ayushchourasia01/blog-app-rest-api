package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("")
    ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }
    @GetMapping("")
    ResponseEntity<List<PostDTO>> getAllPosts(){
        return ResponseEntity.ok(postService.getAllPosts());
    }
    @GetMapping("/{id}")
    ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }
    @PutMapping("/{id}")
    ResponseEntity<PostDTO> updatePostById(@PathVariable("id") Long postId, @RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postService.updatePostById(postId,postDTO));
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePostbyId(@PathVariable("id") Long postId){
        return ResponseEntity.ok("Successfully deleted the post");
    }
}
