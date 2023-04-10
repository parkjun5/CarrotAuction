package com.carrot.web_mvc.user.application.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.ZonedDateTime;


public record UserResponse(
        @Schema(description = "유저 이메일", example = "***@gmail.com")
        @Email String email,
        @Schema(description = "유저 닉네임", example = "Need Buy Mac Book")
        @NotBlank String nickname,
        @Schema(description = "가입 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
        ZonedDateTime createDate) {
}
