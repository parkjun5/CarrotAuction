package com.carrot.web_mvc.bid.domain;

import com.carrot.web_mvc.auction.domain.Auction;
import com.carrot.web_mvc.common.domain.BaseEntity;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;
    @Min(value=1_000, message = "천원보다는 비싼 금액을 입력하세요." )
    @Schema(description = "경매가격", example = "5000")
    private int biddingPrice;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @Schema(description = "입찰 시간" ,type = "string", example = "2023-03-08T00:00:00+0900")
    private ZonedDateTime biddingTime;

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public void changeBid(long bidderId, int biddingPrice, ZonedDateTime biddingTime) {
        this.bidderId = bidderId;
        this.biddingPrice = biddingPrice;
        this.biddingTime = biddingTime;
    }
}
