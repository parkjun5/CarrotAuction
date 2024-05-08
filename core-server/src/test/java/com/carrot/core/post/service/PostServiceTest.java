package com.carrot.core.post.service;

import com.carrot.core.item.domain.Category;
import com.carrot.core.item.domain.Item;
import com.carrot.core.post.application.PostService;
import com.carrot.core.post.application.dto.PostRequest;
import com.carrot.core.post.application.dto.PostResponse;
import com.carrot.core.post.domain.Post;
import com.carrot.core.post.domain.repository.PostRepository;
import com.carrot.core.user.application.UserService;
import com.carrot.core.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
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
    private Post post;

    @Mock
    private User user;
    @Mock
    private PostRequest request;
    @Mock
    private PostResponse response;

    @DisplayName("글 등록 테스트")
    void createPost() {
        //given
        given(userService.findUserById(any())).willReturn(user);
        given(postRepository.save(post)).willReturn(post);

        //when
        postService.createPost(request);

        //then
        then(userService).should(times(1)).findUserById(anyLong());
        then(postRepository).should(times(1)).save(any(Post.class));
    }

    @DisplayName("글 찾기 테스트")
    void findById() {
        //given
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        //when
        postService.findById(anyLong());
        //then
        then(postRepository).should(times(1)).findById(anyLong());
    }

    @DisplayName("글 변경 테스트")
    void updatePost() {
        //given
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        willDoNothing().given(post).changeContent(anyString(), anyString());
        willDoNothing().given(post).changeItem(anyString(), anyInt(), anyString(), any(Category.class));

        given(request.postTitle()).willReturn("Test Title");
        given(request.postContent()).willReturn("Test Content");
        given(request.category()).willReturn(Category.WTB);
        given(request.item()).willReturn(Item.of("test", 10_000, "test data"));

        //when
        assertThatCode(() -> postService.updatePost(1L, request)).doesNotThrowAnyException();

        //then
        then(postRepository).should(times(1)).findById(anyLong());
        then(post).should(times(1)).changeContent(anyString(), anyString());
        then(post).should(times(1)).changeItem(anyString(), anyInt(), anyString(), any(Category.class));
    }


    @Test
    @DisplayName("글 삭제 테스트")
    void deleteById() {
        //given
        given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
        willDoNothing().given(postRepository).delete(any(Post.class));

        //when
        postService.deletePost(anyLong());

        //then
        then(postRepository).should(times(1)).findById(anyLong());
        then(postRepository).should(times(1)).delete(any(Post.class));
    }
}