package com.carrot.auction.domain.auction.dto;

import com.carrot.auction.domain.auction.domain.Bid;
import com.carrot.auction.domain.item.domain.Category;
import com.carrot.auction.domain.item.domain.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.ZonedDateTime;


@Builder
@Schema(description = "경매장 요청 객체")
public record AuctionRequest(
        @NotNull
        @Schema(description = "호스트유저 아이디", example = "1")
        Long userId,
        @NotBlank @Size(max= 10)
        @Schema(description = "경매장 이름", example = "맥북 팝니다")
        String name,
        @Schema(description = "비밀번호", example = "q1w2e3!")
        String password,
        @Min(value= 0) @Max(value= 100)
        @Schema(description = "경매장 최대 인원수", example = "100")
        int limitOfEnrollment,
        @Embedded Bid bid,
        @Embedded Item item,
        @Enumerated(EnumType.STRING)
        @Schema(description = "카테고리", defaultValue = "DIGITAL")
        Category category,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "Asia/Seoul")
        @Schema(description = "경매 시작 일자", type = "string", example = "2023-03-08T00:00:00+0900")
        ZonedDateTime beginAuctionDateTime,
        @NotNull
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "Asia/Seoul")
        @Schema(description = "경매 종료 일자", type = "string", example = "2024-03-08T00:00:00+0900")
        ZonedDateTime closeAuctionDateTime) {
}
