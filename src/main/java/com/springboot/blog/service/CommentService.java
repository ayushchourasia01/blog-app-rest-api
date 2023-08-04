package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.model.CommentsDAO;
import com.springboot.blog.model.PostDAO;

import java.util.List;

public interface CommentService {
    CommentDTO addComment(Long postId, CommentDTO commentDTO);
    List<CommentDTO> getAllCommentsByPostId(Long postId);
    PostDAO getPostDAOById(Long postId);
    CommentDTO getCommentById(Long postId, Long commentId);
    CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO);
    void deleteById(Long postId, Long commentId);
}
