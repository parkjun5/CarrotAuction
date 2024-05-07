package com.carrot.core.auction.application.dto;

import com.carrot.core.auction.application.annotation.EventDateTime;
import com.carrot.core.auction.application.annotation.NotDuplicatedType;
import com.carrot.core.bidrule.application.dto.BidRuleRequest;
import com.carrot.core.item.domain.Category;
import com.carrot.core.item.domain.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;


@Builder
@Schema(description = "경매 요청 객체")
@EventDateTime(beginDateTime = "beginDateTime", closeDateTime = "closeDateTime")
public record AuctionRequest(
        @Min(value = 1_000)
        @Schema(description = "경매 최초 금액", example = "1_000")
        int bidStartPrice,
        @Embedded Item item,
        @Enumerated(EnumType.STRING)
        @Schema(description = "카테고리", defaultValue = "DIGITAL")
        Category category,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "Asia/Seoul")
        @Schema(description = "경매 시작 일자", type = "string", example = "2023-03-08T00:00:00+0900")
        ZonedDateTime beginDateTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "Asia/Seoul")
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        ZonedDateTime closeDateTime,
        @NotDuplicatedType
        List<BidRuleRequest> selectedBidRules) {
}
