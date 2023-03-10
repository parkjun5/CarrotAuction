package com.carrot.auction.domain.post.domain.repository;

import com.carrot.auction.domain.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
