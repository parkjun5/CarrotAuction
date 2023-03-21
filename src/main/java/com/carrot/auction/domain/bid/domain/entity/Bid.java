package com.carrot.auction.domain.bid.domain.entity;

import com.carrot.auction.domain.auction.domain.entity.AuctionRoom;
import com.carrot.auction.global.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @OneToOne(mappedBy = "bid")
    private AuctionRoom auctionRoom;
    @Min(value=1_000, message = "천원보다는 비싼 금액을 입력하세요." )
    @Schema(description = "경매가격", example = "5000")
    private int biddingPrice;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    @Schema(description = "입찰 시간" ,type = "string", example = "2023-03-08T00:00:00+0900")
    private ZonedDateTime biddingTime;
    @OneToMany(mappedBy = "bid")
    @Builder.Default
    private List<BidRuleBook> bidRuleBooks = new ArrayList<>();

    public void setAuctionRoom(AuctionRoom auctionRoom) {
        this.auctionRoom = auctionRoom;
    }

    public void changeBid(long bidderId, int biddingPrice, ZonedDateTime biddingTime) {
        this.bidderId = bidderId;
        this.biddingPrice = biddingPrice;
        this.biddingTime = biddingTime;
    }
}
