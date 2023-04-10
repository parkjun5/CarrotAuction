package com.carrot.web_mvc.user.presentation;

import com.carrot.web_mvc.user.application.dto.UserRequest;
import com.carrot.web_mvc.user.application.dto.UserResponse;
import com.carrot.web_mvc.user.application.UserService;
import com.carrot.web_mvc.common.dto.ApiCommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "user", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiCommonResponse<Page<UserResponse>>> getUserList
            (@PageableDefault(direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(ApiCommonResponse.success("users",userService.getUsersByPageable(pageable) ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiCommonResponse<UserResponse>> getUserById
            (@PathVariable final Long userId) {
        return ResponseEntity.ok(ApiCommonResponse.success("users",userService.getUserById(userId) ));
    }

    @PostMapping
    public ResponseEntity<ApiCommonResponse<Object>> createUser
            (@RequestBody @Valid UserRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiCommonResponse.success("계정 생성이 완료되었습니다", userService.createUser(request)));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<ApiCommonResponse<UserResponse>> updateUser
            (@PathVariable final Long userId, @RequestBody @Valid UserRequest request) {
        return ResponseEntity.ok(ApiCommonResponse.success("user", userService.updateUser(userId, request)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiCommonResponse<Long>> updateUser
            (@PathVariable final Long userId) {
        return ResponseEntity.ok(ApiCommonResponse.success("deleted User Id", userService.deleteUser(userId)));
    }
}
