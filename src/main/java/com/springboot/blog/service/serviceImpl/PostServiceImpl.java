package com.springboot.blog.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.dto.PostDTO;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.model.PostDAO;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        PostDAO postDAO = mapper.convertValue(postDTO, PostDAO.class);
        PostDAO res = repo.save(postDAO);
        return mapper.convertValue(res,PostDTO.class);
    }
    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize, sort);
        Page<PostDAO> posts = repo.findAll(pageRequest);
        List<PostDAO> postDAOList = posts.getContent();
        List<PostDTO> postDTOList = postDAOList.stream().map(postDAO -> mapper.convertValue(postDAO, PostDTO.class))
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
        PostDAO postDAO = getPostDAOById(postId);
        return mapper.convertValue(postDAO, PostDTO.class);
    }

    @Override
    public PostDTO updatePostById(Long postId, PostDTO postDTO){
        PostDAO postDAO = getPostDAOById(postId);
        if (postDTO.getDescription() != null) {
            postDAO.setDescription(postDTO.getDescription());
        }
        if (postDTO.getContent() != null) {
            postDAO.setContent(postDTO.getContent());
        }
        if (postDTO.getTitle() != null) {
            postDAO.setTitle(postDTO.getTitle());
        }
        repo.save(postDAO);
        return mapper.convertValue(postDAO, PostDTO.class);
    }

    @Override
    public void deleteById(Long postId) {
        PostDAO postDAO = getPostDAOById(postId);
        repo.delete(postDAO);
    }

    private PostDAO getPostDAOById(Long postId) {
        return repo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
    }
}
