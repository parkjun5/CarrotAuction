package com.carrot.auction.domain.auction.domain;

import com.carrot.auction.domain.auction.dto.BiddingRequest;
import com.carrot.auction.domain.auction.exception.IllegalAuctionTimeException;
import com.carrot.auction.domain.auction.exception.NotEnoughBiddingPriceException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class AuctionValidator {

    public void correctAuctionTime(ZonedDateTime beginAuctionDateTime, ZonedDateTime closeAuctionDateTime) {
        if (beginAuctionDateTime.isAfter(closeAuctionDateTime)) {
            throw new IllegalAuctionTimeException("시작 날짜: " + beginAuctionDateTime + ", 종료 날짜: " + closeAuctionDateTime + "종료 날짜보다 시작 날짜가 이릅니다.");
        }
        if (LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).isAfter(closeAuctionDateTime)) {
            throw new IllegalAuctionTimeException("종료 날짜: " + closeAuctionDateTime + "이미 종료된 날짜입니다.");
        }
    }

    public void bidTimeBetweenAuctionTime(ZonedDateTime bidTime, ZonedDateTime beginAuctionDateTime, ZonedDateTime closeAuctionDateTime) {
        if (bidTime.isBefore(beginAuctionDateTime)) {
            throw new IllegalAuctionTimeException("아직 시작하지 않은 경매입니다.");
        }
        if (bidTime.isAfter(closeAuctionDateTime)) {
            throw new IllegalAuctionTimeException("이미 종료된 경매입니다.");
        }
    }

    public void bidPriceHigherThanMinimum(int bidPrice, int existPrice) {
        int minimumPrice = BiddingRequest.getMinimumPrice(existPrice);
        if (minimumPrice >= bidPrice) {
            throw new NotEnoughBiddingPriceException(minimumPrice + "보다 제시하신 금액보다 낮습니다.");
        }
    }

}
