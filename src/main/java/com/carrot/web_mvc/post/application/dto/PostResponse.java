package com.carrot.web_mvc.post.application.dto;

import com.carrot.web_mvc.item.domain.Category;
import com.carrot.web_mvc.item.domain.Item;
import com.carrot.web_mvc.post.domain.PostStatus;
import com.carrot.web_mvc.user.application.dto.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record PostResponse(
        @Schema(description = "작성자", example = "mac seller")
        UserResponse user,
        @Schema(description = "글 제목", example = "[판매] 맥북 판매 경기/수원")
        String postTitle,
        @Schema(description = "글 내용", example = "맥북 판매합니다 경기/수원")
        String postContent,
        @Embedded Item item,
        @Schema(description = "카테고리", example = "DIGITAL")
        @Enumerated(EnumType.STRING) Category category,
        @Schema(description = "조회수", example = "10")
        int views,
        @Schema(description = "좋아요", example = "3")
        int amountOfInterest,
        @Schema(description = "상태", example = "IN_SALE")
        @Enumerated(EnumType.STRING) PostStatus postStatus) {
}
