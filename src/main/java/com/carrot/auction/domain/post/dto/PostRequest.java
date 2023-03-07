package com.carrot.auction.domain.post.dto;

import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PostRequest(
        @NotNull Long userId,
        @NotBlank String title,
        @NotBlank String postContent,
        @Embedded Item item,
        @Enumerated(EnumType.STRING) Category category) {
}
