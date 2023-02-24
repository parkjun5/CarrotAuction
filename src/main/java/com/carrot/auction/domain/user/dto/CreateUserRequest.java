package com.carrot.auction.domain.user.dto;


import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest (
        @NotBlank String email,
        @NotBlank String nickname,
        @NotBlank String password) {
}
