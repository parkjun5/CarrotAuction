package com.carrot.core.bid.domain;

import com.carrot.core.auction.domain.Auction;
import com.carrot.core.bid.application.dto.BidRequest;
import com.carrot.core.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Entity
@Getter
@Builder @AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bid extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "bid_id")
    private Long id;
    @Schema(description = "입찰자 명", example = "1")
    private long bidderId;
    @Column(name = "auction_id")
    private Long auctionId;
    @Min(value=1_000, message = "천원보다는 비싼 금액을 입력하세요." )
    @Schema(description = "경매가격", example = "5000")
    private int biddingPrice;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @Schema(description = "입찰 시간" ,type = "string", example = "2023-03-08T00:00:00+0900")
    private ZonedDateTime biddingTime;

    public static Bid of(BidRequest request, Long auctionId) {
        Bid bid = new Bid();
        bid.auctionId = auctionId;
        bid.biddingPrice = request.biddingPrice();
        bid.biddingTime = ZonedDateTime.now();
        bid.bidderId = request.bidderId();
        return bid;
    }

    public void setAuction(Auction auction) {
        this.auctionId = auction.getId();
    }

    public void changeBid(long bidderId, int biddingPrice, ZonedDateTime biddingTime) {
        this.bidderId = bidderId;
        this.biddingPrice = biddingPrice;
        this.biddingTime = biddingTime;
    }
}
