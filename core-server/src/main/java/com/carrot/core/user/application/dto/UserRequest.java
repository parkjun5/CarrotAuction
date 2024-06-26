package com.carrot.core.user.application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @Email String email,
        @NotBlank String nickname,
        @NotBlank String password) {
}
