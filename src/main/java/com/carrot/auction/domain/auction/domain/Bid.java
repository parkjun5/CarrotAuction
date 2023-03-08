package com.carrot.auction.domain.auction.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Bid {
    private long bidderId;
    private int biddingPrice;
    private ZonedDateTime biddingTime;

    public void changeStartPrice(int biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

    public void changeBid(long bidderId, int biddingPrice, ZonedDateTime biddingTime) {
        this.bidderId = bidderId;
        this.biddingPrice = biddingPrice;
        this.biddingTime = biddingTime;
    }
}
