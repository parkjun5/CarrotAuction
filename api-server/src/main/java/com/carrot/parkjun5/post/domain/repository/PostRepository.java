package com.carrot.parkjun5.post.domain.repository;

import com.carrot.parkjun5.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
