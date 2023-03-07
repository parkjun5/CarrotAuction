package com.carrot.auction.domain.post.service;

import com.carrot.auction.domain.post.domain.entity.Post;
import com.carrot.auction.domain.post.domain.repository.PostRepository;
import com.carrot.auction.domain.post.dto.PostMapper;
import com.carrot.auction.domain.post.dto.PostRequest;
import com.carrot.auction.domain.post.dto.PostResponse;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final PostMapper postMapper;

    public PostResponse createPost(PostRequest request) {
        User writer = userService.findUserById(request.userId())
                .orElseThrow(() -> new NoSuchElementException("계정이 존재하지 않습니다."));
        Post post = postMapper.toEntityByRequest(writer, request);
        return postMapper.toResponseByEntity(postRepository.save(post));
    }

    public PostResponse findById(final Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("포스트가 존재하지 않습니다."));
        return postMapper.toResponseByEntity(post);
    }



}
