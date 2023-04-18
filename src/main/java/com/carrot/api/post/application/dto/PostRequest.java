package com.carrot.api.post.application.dto;

import com.carrot.api.item.domain.Category;
import com.carrot.api.item.domain.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PostRequest(
        @Schema(description = "작성자 아이디", example = "1")
        @NotNull Long userId,
        @Schema(description = "글 제목", example = "[판매] 맥북 판매 경기/수원")
        @NotBlank String postTitle,
        @Schema(description = "글 내용", example = "맥북 판매합니다 경기/수원")
        @NotBlank String postContent,
        @Embedded Item item,
        @Schema(description = "카테고리", example = "DIGITAL")
        @Enumerated(EnumType.STRING) Category category) {
}
