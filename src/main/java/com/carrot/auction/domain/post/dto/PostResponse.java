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
        @Schema(description = "작성자")
        @JsonSerialize(using = UserSerializer.class) User user,
        String title,
        String postContent,
        @Embedded Item item,
        @Enumerated(EnumType.STRING) Category category,
        int views,
        int amountOfInterest,
        @Enumerated(EnumType.STRING) PostStatus postStatus) {
}
