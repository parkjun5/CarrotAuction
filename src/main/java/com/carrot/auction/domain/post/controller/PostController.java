package com.carrot.auction.domain.post.controller;

import com.carrot.auction.domain.post.dto.PostRequest;
import com.carrot.auction.domain.post.dto.PostResponse;
import com.carrot.auction.domain.post.service.PostService;
import com.carrot.auction.global.dto.ApiCommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(ApiCommonResponse.success("post", postService.findById(postId)));
    }

    @PostMapping
    public ResponseEntity<ApiCommonResponse<PostResponse>> createPost
            (@RequestBody @Valid PostRequest request) {
        return ResponseEntity.ok(ApiCommonResponse.success("post", postService.createPost(request)));
    }

}
