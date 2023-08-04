package com.springboot.blog.service;

import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.model.PostDAO;

import java.util.List;

public interface PostService{
    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long postId);
    PostDTO updatePostById(Long postId, PostDTO postDTO);
    void deleteById(Long postId);
}
