package com.carrot.core.post.application;

import com.carrot.core.post.domain.Post;
import com.carrot.core.post.domain.repository.PostRepository;
import com.carrot.core.post.application.dto.PostMapper;
import com.carrot.core.post.application.dto.PostRequest;
import com.carrot.core.post.application.dto.PostResponse;
import com.carrot.core.user.domain.User;
import com.carrot.core.user.application.UserService;
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
    private static final String POST_NOT_FOUND = " 포스트가 존재하지 않습니다.";

    @Transactional
    public PostResponse createPost(PostRequest request) {
        User writer = userService.findUserById(request.userId());
        Post post = postMapper.toEntityByRequest(writer, request);
        return postMapper.toResponseByEntity(postRepository.save(post));
    }

    public PostResponse findById(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(postId + POST_NOT_FOUND));
        return postMapper.toResponseByEntity(post);
    }

    @Transactional
    public PostResponse updatePost(final Long postId, PostRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(postId + POST_NOT_FOUND));
        post.changeContent(request.postTitle(), request.postContent());
        post.changeItem(request.item().getTitle(), request.item().getPrice(), request.item().getContent(), request.category());
        return postMapper.toResponseByEntity(post);
    }

    @Transactional
    public Long deletePost(final Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException(postId + POST_NOT_FOUND));
        postRepository.delete(post);
        return postId;
    }
}
