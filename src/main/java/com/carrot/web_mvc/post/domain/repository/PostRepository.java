package com.carrot.web_mvc.post.domain.repository;

import com.carrot.web_mvc.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
