package com.carrot.api.auction.application.dto;

import com.carrot.api.auction.domain.AuctionStatus;
import com.carrot.api.bidrule.application.dto.BidRuleResponse;
import com.carrot.api.item.domain.Category;
import com.carrot.api.item.domain.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Schema(description = "경매장 응답 객체")
public record AuctionResponse(
        @Schema(description = "경매 아이디")
        long auctionId,
        @Schema(description = "경매 최초 금액", example = "1_000")
        int bidStartPrice,
        @Embedded Item item,
        @Schema(description = "카테고리", defaultValue = "DIGITAL")
        @Enumerated(EnumType.STRING) Category category,
        @Schema(description = "경매 시작 일자", type = "string", example = "2023-03-08T00:00:00+0900")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") ZonedDateTime beginDateTime,
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ") ZonedDateTime closeDateTime,
        @Schema(description = "경매장 상태", defaultValue = "DRAFT")
        @Enumerated(EnumType.STRING) AuctionStatus auctionStatus,
        List<BidRuleResponse> selectedRules) {

}
