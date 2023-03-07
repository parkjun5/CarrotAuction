package com.carrot.auction.domain.post.service;

import com.carrot.auction.domain.post.domain.entity.Post;
import com.carrot.auction.domain.post.domain.repository.PostRepository;
import com.carrot.auction.domain.post.dto.PostMapper;
import com.carrot.auction.domain.post.dto.PostRequest;
import com.carrot.auction.domain.post.dto.PostResponse;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private UserService userService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private Post post;

    @Mock
    private User user;
    @Mock
    private PostRequest request;

    @Mock
    private PostResponse response;

    @Test
    @DisplayName("글 등록 테스트")
    void createPost() {
        //given
        given(userService.findUserById(any())).willReturn(Optional.of(user));
        given(postMapper.toEntityByRequest(user, request)).willReturn(post);
        given(postRepository.save(post)).willReturn(post);
        given(postMapper.toResponseByEntity(post)).willReturn(response);

        //when
        postService.createPost(request);

        //then
        then(userService).should(times(1)).findUserById(anyLong());
        then(postMapper).should(times(1)).toEntityByRequest(any(User.class), any(PostRequest.class));
        then(postRepository).should(times(1)).save(any(Post.class));
        then(postMapper).should(times(1)).toResponseByEntity(any(Post.class));
    }

}