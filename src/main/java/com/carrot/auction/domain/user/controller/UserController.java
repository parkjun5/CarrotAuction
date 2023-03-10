package com.carrot.auction.domain.user.controller;

import com.carrot.auction.domain.user.dto.CreateUserRequest;
import com.carrot.auction.domain.user.service.UserService;
import com.carrot.auction.global.dto.ApiCommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "user", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiCommonResponse<Object>> createUser
            (@RequestBody CreateUserRequest createUserRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiCommonResponse.success("계정 생성이 완료되었습니다", userService.createUser(createUserRequest)));
    }
}
