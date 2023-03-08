package com.carrot.auction.domain.post.dto;

import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.carrot.auction.domain.post.domain.entity.PostStatus;
import com.carrot.auction.domain.user.domain.entity.User;
import com.carrot.auction.domain.user.serailizer.UserSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record PostResponse(
        @Schema(description = "작성자", example = "mac seller")
        @JsonSerialize(using = UserSerializer.class) User user,
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
