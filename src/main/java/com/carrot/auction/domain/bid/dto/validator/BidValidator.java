package com.carrot.auction.domain.bid.dto.validator;

import com.carrot.auction.domain.auction.exception.IllegalAuctionTimeException;
import com.carrot.auction.domain.auction.exception.NotEnoughBiddingPriceException;
import com.carrot.auction.domain.bid.dto.BidRequest;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class BidValidator {
    public void bidTimeBetweenAuctionTime(ZonedDateTime bidTime, ZonedDateTime beginDateTime, ZonedDateTime closeDateTime) {
        if (bidTime.isBefore(beginDateTime)) {
            throw new IllegalAuctionTimeException("아직 시작하지 않은 경매입니다.");
        }
        if (bidTime.isAfter(closeDateTime)) {
            throw new IllegalAuctionTimeException("이미 종료된 경매입니다.");
        }
    }

    public void bidPriceHigherThanMinimum(int bidPrice, int existPrice) {
        int minimumPrice = BidRequest.getMinimumPrice(existPrice);
        if (minimumPrice >= bidPrice) {
            throw new NotEnoughBiddingPriceException("최소금액 " + minimumPrice + "보다 제시하신 금액보다 낮습니다.");
        }
    }

}
