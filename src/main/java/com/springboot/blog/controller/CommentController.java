package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/posts")
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{postId}/comments")
    ResponseEntity<CommentDTO> addComment(@PathVariable Long postId, @RequestBody CommentDTO commentDTO){
        return new ResponseEntity<>(commentService.addComment(postId,commentDTO), HttpStatus.CREATED);
    }
    @GetMapping("/{postId}/comments")
    ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long postId){
        return ResponseEntity.ok(commentService.getAllCommentsByPostId(postId));
    }
    @GetMapping("/{postId}/comments/{id}")
    ResponseEntity<CommentDTO> getCommentById(@PathVariable Long postId, @PathVariable("id") Long commentId){
        return ResponseEntity.ok(commentService.getCommentById(postId,commentId));
    }
    @PutMapping("/{postId}/comments/{id}")
    ResponseEntity<CommentDTO> updatePostById(@PathVariable Long postId,
                                           @PathVariable("id") Long commentId,
                                           @RequestBody CommentDTO commentDTO){
        return ResponseEntity.ok(commentService.updateCommentById(postId,commentId,commentDTO));
    }
    @DeleteMapping("/{postId}/comments/{id}")
    ResponseEntity<String> deleteCommentById(@PathVariable Long postId,@PathVariable("id") Long commentId){
        commentService.deleteById(postId,commentId);
        return ResponseEntity.ok("Successfully deleted the post");
    }
}
