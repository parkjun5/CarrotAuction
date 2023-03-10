package com.carrot.auction.domain.auction.domain;

import com.carrot.auction.domain.auction.dto.BiddingRequest;
import com.carrot.auction.domain.auction.exception.AlreadyFullEnrollmentException;
import com.carrot.auction.domain.auction.exception.IllegalAuctionTimeException;
import com.carrot.auction.domain.auction.exception.NotEnoughBiddingPriceException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class AuctionValidator {

    public void correctAuctionTime(ZonedDateTime beginDateTime, ZonedDateTime closeDateTime) {
        if (beginDateTime.isAfter(closeDateTime)) {
            throw new IllegalAuctionTimeException("시작 날짜: " + beginDateTime + ", 종료 날짜: " + closeDateTime + "종료 날짜보다 시작 날짜가 이릅니다.");
        }
        if (LocalDateTime.now().atZone(ZoneId.of("Asia/Seoul")).isAfter(closeDateTime)) {
            throw new IllegalAuctionTimeException("종료 날짜: " + closeDateTime + "이미 종료된 날짜입니다.");
        }
    }

    public void bidTimeBetweenAuctionTime(ZonedDateTime bidTime, ZonedDateTime beginDateTime, ZonedDateTime closeDateTime) {
        if (bidTime.isBefore(beginDateTime)) {
            throw new IllegalAuctionTimeException("아직 시작하지 않은 경매입니다.");
        }
        if (bidTime.isAfter(closeDateTime)) {
            throw new IllegalAuctionTimeException("이미 종료된 경매입니다.");
        }
    }

    public void bidPriceHigherThanMinimum(int bidPrice, int existPrice) {
        int minimumPrice = BiddingRequest.getMinimumPrice(existPrice);
        if (minimumPrice >= bidPrice) {
            throw new NotEnoughBiddingPriceException("최소금액 " + minimumPrice + "보다 제시하신 금액보다 낮습니다.");
        }
    }

    public void isFullEnrollment(int numberOfCurrentEnrollment, int limitOfEnrollment) {
        if (limitOfEnrollment <= numberOfCurrentEnrollment) {
            throw new AlreadyFullEnrollmentException("경매장이 꽉찼어요.");
        }
    }

}
