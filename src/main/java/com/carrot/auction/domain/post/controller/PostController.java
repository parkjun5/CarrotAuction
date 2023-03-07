package com.carrot.auction.domain.post.controller;

import com.carrot.auction.domain.post.dto.PostRequest;
import com.carrot.auction.domain.post.dto.PostResponse;
import com.carrot.auction.domain.post.service.PostService;
import com.carrot.auction.global.dto.ApiCommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<ApiCommonResponse<PostResponse>> getPostById
            (@PathVariable(value = "postId") final Long postId) {
        return ResponseEntity
                .ok(ApiCommonResponse.success("post", postService.findById(postId)));
    }

    @PostMapping
    public ResponseEntity<ApiCommonResponse<PostResponse>> createPost
            (@RequestBody @Valid PostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiCommonResponse.success("post", postService.createPost(request)));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ApiCommonResponse<PostResponse>> updatePost
            (@PathVariable(value = "postId") final Long postId, @RequestBody @Valid PostRequest request) {
        return ResponseEntity
                .ok(ApiCommonResponse.success("post", postService.updatePost(postId, request)));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiCommonResponse<Long>> deletePost
            (@PathVariable(value = "postId") final Long postId) {
        return ResponseEntity
                .ok(ApiCommonResponse.success("post", postService.deletePost(postId)));
    }
}
