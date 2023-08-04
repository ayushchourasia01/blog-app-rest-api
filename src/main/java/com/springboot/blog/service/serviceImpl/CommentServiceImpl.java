package com.springboot.blog.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.dto.CommentDTO;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.CommentsDAO;
import com.springboot.blog.model.PostDAO;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ObjectMapper mapper;
    private final CommentRepository repo;
    private final PostRepository postRepository;
    @Override
    public CommentDTO addComment(Long postId, CommentDTO commentDTO) {
        CommentsDAO commentsDAO = mapper.convertValue(commentDTO, CommentsDAO.class);
        PostDAO post = getPostDAOById(postId);
        commentsDAO.setPost(post);
        repo.save(commentsDAO);
        return mapper.convertValue(commentsDAO, CommentDTO.class);
    }

    @Override
    public List<CommentDTO> getAllCommentsByPostId(Long postId) {
        PostDAO post = getPostDAOById(postId);
        Set<CommentsDAO> commentList = post.getComments();
        return commentList.stream().map(comment -> mapper.convertValue(comment, CommentDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PostDAO getPostDAOById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
    }
    private CommentsDAO getCommentDAOById(Long commentId) {
        return repo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","id",commentId));
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        CommentsDAO comment = isCommentBelongingToGivenPost(postId,commentId);
        return mapper.convertValue(comment, CommentDTO.class);
    }

    @Override
    public CommentDTO updateCommentById(Long postId, Long commentId, CommentDTO commentDTO) {
        CommentsDAO comment = isCommentBelongingToGivenPost(postId,commentId);
        if(commentDTO.getBody() != null){
            comment.setBody(commentDTO.getBody());
        }
        if(commentDTO.getName() != null){
            comment.setName(commentDTO.getName());
        }
        if(commentDTO.getEmailId() != null){
            comment.setEmailId(commentDTO.getEmailId());
        }
        repo.save(comment);
        return mapper.convertValue(comment, CommentDTO.class);
    }

    @Override
    public void deleteById(Long postId, Long commentId) {
        CommentsDAO comment = isCommentBelongingToGivenPost(postId,commentId);
        repo.delete(comment);
    }
    private CommentsDAO isCommentBelongingToGivenPost(Long postId, Long commentId){
        PostDAO post = getPostDAOById(postId);
        CommentsDAO comment = getCommentDAOById(commentId);
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to this post");
        }
        return comment;
    }

}
