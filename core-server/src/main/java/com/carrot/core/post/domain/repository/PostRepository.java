package com.carrot.core.post.domain.repository;

import com.carrot.core.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
