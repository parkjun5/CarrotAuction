package com.carrot.parkjun5.user.application.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @Email String email,
        @NotBlank String nickname,
        @NotBlank String password) {
}
