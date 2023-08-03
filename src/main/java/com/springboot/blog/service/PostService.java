package com.springboot.blog.service;

import com.springboot.blog.dto.PostDTO;

import java.util.List;

public interface PostService{
    PostDTO createPost(PostDTO postDTO);
    List<PostDTO> getAllPosts();
    PostDTO getPostById(Long postId);
    PostDTO updatePostById(Long postId, PostDTO postDTO);
    void deleteById(Long postId);
}
