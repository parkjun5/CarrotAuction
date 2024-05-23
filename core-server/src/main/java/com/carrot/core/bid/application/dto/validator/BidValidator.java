package com.carrot.core.bid.application.dto.validator;

import com.carrot.core.auction.application.AuctionService;
import com.carrot.core.auction.domain.Auction;
import com.carrot.core.auction.domain.AuctionStatus;
import com.carrot.core.auction.exception.IllegalAuctionTimeException;
import com.carrot.core.bid.application.annotation.BidRequestCheck;
import com.carrot.core.bid.application.dto.BidRequest;
import com.carrot.core.bid.exception.AlreadyEndAuctionException;
import com.carrot.core.bidrule.application.BiddingRuleValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@RequiredArgsConstructor
public class BidValidator implements ConstraintValidator<BidRequestCheck, BidRequest> {

    private final AuctionService auctionService;
    private final BiddingRuleValidator biddingRuleValidator;
    private static final String ALREADY_CLOSE_MESSAGE = "이미 종료된 경매입니다.";

    @Override
    public void initialize(BidRequestCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BidRequest request, ConstraintValidatorContext context) {
        Long auctionId = request.auctionId();
        Auction auction = auctionService.findAuctionById(auctionId);
        LocalDateTime biddingTime = request.biddingTime();

        if (biddingTime.isAfter(auction.getCloseDateTime().toLocalDateTime())) {
            auctionService.closeAuction(auctionId);
            throw new AlreadyEndAuctionException(ALREADY_CLOSE_MESSAGE);
        }

        AuctionStatus auctionStatus = auction.getAuctionStatus();

        if (biddingTime.isBefore(auction.getBeginDateTime().toLocalDateTime())) {
            throw new IllegalAuctionTimeException("아직 시작하지 않은 경매입니다.");
        }

        if (auctionStatus == AuctionStatus.DRAFT) {
            auctionService.openAuction(auction.getId());
        }

        if (auctionStatus == AuctionStatus.END_ENROLLMENT) {
            throw new AlreadyEndAuctionException(ALREADY_CLOSE_MESSAGE);
        }

        biddingRuleValidator.validateByBiddingRule(request, auction);
        return true;
    }
}
