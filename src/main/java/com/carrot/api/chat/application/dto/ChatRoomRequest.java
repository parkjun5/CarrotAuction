package com.carrot.api.chat.application.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record ChatRoomRequest (
    @NotNull
    Long userId,
    @NotBlank @Size(max= 40)
    String name) {
}
