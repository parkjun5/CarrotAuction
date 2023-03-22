package com.carrot.parkjun5.bid.application.dto.validator;

import com.carrot.parkjun5.auctionroom.domain.AuctionRoom;
import com.carrot.parkjun5.auctionroom.application.AuctionRoomService;
import com.carrot.parkjun5.bid.application.annotation.BidRequestCheck;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class BidValidator implements ConstraintValidator<BidRequestCheck, BidRequest> {

    private final AuctionRoomService auctionRoomService;

    @Override
    public void initialize(BidRequestCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BidRequest request, ConstraintValidatorContext context) {
        AuctionRoom auctionRoom = auctionRoomService.findAuctionRoomById(request.roomId());
//        ZonedDateTime biddingTime = request.biddingTime();
//        if (biddingTime.isBefore(auctionRoom.getBeginDateTime())) {
//            throw new IllegalAuctionTimeException("아직 시작하지 않은 경매입니다.");
//        }
//        if (biddingTime.isAfter(auctionRoom.getCloseDateTime())) {
//            throw new IllegalAuctionTimeException("이미 종료된 경매입니다.");
//        }
//
//        int existPrice;
//        if (auctionRoom.getBid() == null) {
//            existPrice = auctionRoom.getBidStartPrice();
//        } else {
//            existPrice = auctionRoom.getBid().getBiddingPrice();
//        }
//
//        int minimumPrice = BidRequest.getMinimumPrice(existPrice);
//        if (minimumPrice >= request.biddingPrice()) {
//            throw new NotEnoughBiddingPriceException("최소금액 " + minimumPrice + "보다 제시하신 금액보다 낮습니다.");
//        }

        return true;
    }
}
