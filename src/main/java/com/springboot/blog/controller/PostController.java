package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.dto.UpdateGroups;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.ConstantUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    ResponseEntity<PostDTO> createPost( @Validated(UpdateGroups.ValidateAll.class) @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }
    @GetMapping("")
    ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = ConstantUtil.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = ConstantUtil.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ConstantUtil.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ConstantUtil.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ){
        return ResponseEntity.ok(postService.getAllPosts(pageNo,pageSize,sortBy,sortDir));
    }
    @GetMapping("/{id}")
    ResponseEntity<PostDTO> getPostById(@PathVariable("id") Long postId){
        return ResponseEntity.ok(postService.getPostById(postId));
    }
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @PutMapping("/{id}")
    ResponseEntity<PostDTO> updatePostById(@PathVariable("id") Long postId,@Validated(UpdateGroups.ValidatePartial.class) @RequestBody PostDTO postDTO){
        return ResponseEntity.ok(postService.updatePostById(postId,postDTO));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deletePostById(@PathVariable("id") Long postId){
        postService.deleteById(postId);
        return ResponseEntity.ok("Successfully deleted the post");
    }
}
