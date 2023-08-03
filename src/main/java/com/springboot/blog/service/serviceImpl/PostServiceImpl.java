package com.springboot.blog.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.Post;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.lang.module.ResolutionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    @Autowired
    PostRepository repo;
    private final ObjectMapper mapper;
    @Override
    public PostDTO createPost(PostDTO postDTO){
        Post post = mapper.convertValue(postDTO, Post.class);
        Post res = repo.save(post);
        return mapper.convertValue(res,PostDTO.class);
    }
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = repo.findAll(pageRequest);
        List<Post> postList = posts.getContent();
        List<PostDTO> postDTOList = postList.stream().map(post -> mapper.convertValue(post, PostDTO.class))
                .collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDTOList);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long postId){
        Post post = repo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        return mapper.convertValue(post, PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(Long postId, PostDTO postDTO){
        Post post = repo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        if (postDTO.getDescription() != null) {
            post.setDescription(postDTO.getDescription());
        }
        if (postDTO.getContent() != null) {
            post.setContent(postDTO.getContent());
        }
        if (postDTO.getTitle() != null) {
            post.setTitle(postDTO.getTitle());
        }
        repo.save(post);
        return mapper.convertValue(post, PostDTO.class);
    }

    @Override
    public void deleteById(Long postId) {
        Post post = repo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        repo.delete(post);
    }
}
