package com.springboot.blog.repository;

import com.springboot.blog.model.PostDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostDAO,Long> {

}
