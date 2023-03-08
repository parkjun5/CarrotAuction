package com.carrot.auction.domain.auction.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Bid {
    @Schema(description = "입찰자 명", example = "1")
    private long bidderId;
    @Min(value=1_000, message = "천원보다는 비싼 금액을 입력하세요." )
    @Schema(description = "경매가격", example = "5000")
    private int biddingPrice;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @Schema(description = "입찰 시간" ,type = "string", example = "2023-03-08T00:00:00+0900")
    private ZonedDateTime biddingTime;

    public static Bid startPrice(int biddingPrice) {
        Bid bid = new Bid();
        bid.biddingPrice = biddingPrice;
        return bid;
    }

    public void changeStartPrice(int biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    public void changeBid(long bidderId, int biddingPrice, ZonedDateTime biddingTime) {
        this.bidderId = bidderId;
        this.biddingPrice = biddingPrice;
        this.biddingTime = biddingTime;
    }
}
