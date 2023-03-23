package com.carrot.parkjun5.bid.application.dto.validator;

import com.carrot.parkjun5.auction.application.AuctionService;
import com.carrot.parkjun5.auction.application.dto.AuctionResponse;
import com.carrot.parkjun5.auction.domain.AuctionStatus;
import com.carrot.parkjun5.auction.exception.IllegalAuctionTimeException;
import com.carrot.parkjun5.bid.application.annotation.BidRequestCheck;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;


@RequiredArgsConstructor
public class BidValidator implements ConstraintValidator<BidRequestCheck, BidRequest> {

    private final AuctionService auctionService;
    private static final String ALREADY_CLOSE_MESSAGE = "이미 종료된 경매입니다.";

    @Override
    public void initialize(BidRequestCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BidRequest request, ConstraintValidatorContext context) {
        Long auctionId = request.auctionId();
        AuctionResponse auctionResponse = auctionService.findAuctionResponseById(auctionId);
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime biddingTime = request.biddingTime();

        boolean isFirstBid = auctionResponse.auctionStatus() == AuctionStatus.PUBLISHED;
        if (isFirstBid) {
            auctionService.openAuction(auctionId);
        }

        if (now.isAfter(auctionResponse.closeDateTime())) {
            auctionService.closeAuction(auctionId);
            throw new IllegalAuctionTimeException(ALREADY_CLOSE_MESSAGE);
        }

        boolean isNotBeginAuction = auctionResponse.auctionStatus() != AuctionStatus.BEGAN_ENROLLMENT;
        boolean beforeThanBeginTime = biddingTime.isBefore(auctionResponse.beginDateTime());
        if (isNotBeginAuction || beforeThanBeginTime) {
            throw new IllegalAuctionTimeException("아직 시작하지 않은 경매입니다.");
        }

        boolean alreadyEndAuction = biddingTime.isAfter(auctionResponse.closeDateTime());
        if (alreadyEndAuction) {
            throw new IllegalAuctionTimeException(ALREADY_CLOSE_MESSAGE);
        }

        return true;
    }
}
