package com.carrot.auction.domain.post.controller;

import com.carrot.auction.domain.post.dto.PostRequest;
import com.carrot.auction.domain.post.dto.PostResponse;
import com.carrot.auction.domain.post.service.PostService;
import com.carrot.auction.global.dto.ApiCommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "post", description = "글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    @Operation(summary = "글 조회", description = "postId로 글을 조회한다.")
    public ResponseEntity<ApiCommonResponse<PostResponse>> getPostById
            (@PathVariable(value = "postId") final Long postId) {
        return ResponseEntity
                .ok(ApiCommonResponse.success("post", postService.findById(postId)));
    }

    @PostMapping
    @Operation(summary = "글 작성", description = "PostRequest 를 기반으로 글을 생성하고 저장한다.")
    public ResponseEntity<ApiCommonResponse<PostResponse>> createPost
            (@RequestBody @Valid PostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiCommonResponse.success("post", postService.createPost(request)));
    }

    @PostMapping("/{postId}")
    @Operation(summary = "글 수정", description = "postId와 PostRequest 를 통해 글을 수정한다.")
    public ResponseEntity<ApiCommonResponse<PostResponse>> updatePost
            (@PathVariable(value = "postId") final Long postId, @RequestBody @Valid PostRequest request) {
        return ResponseEntity
                .ok(ApiCommonResponse.success("post", postService.updatePost(postId, request)));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "글 삭제", description = "postId 글을 삭제한다.")
    public ResponseEntity<ApiCommonResponse<Long>> deletePost
            (@PathVariable(value = "postId") final Long postId) {
        return ResponseEntity
                .ok(ApiCommonResponse.success("post", postService.deletePost(postId)));
    }
}
