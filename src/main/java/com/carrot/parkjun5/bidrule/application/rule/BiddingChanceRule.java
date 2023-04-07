package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.auction.application.AuctionService;
import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bid.exception.AlreadyUseBidChanceException;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@BidRuleName("ChanceRule")
@RequiredArgsConstructor
public class BiddingChanceRule {
    private final AuctionService auctionService;

    public void validate(BidRequest req, Auction auction, String ruleValue) {
        int chanceLimit;
        try {
            chanceLimit = Integer.parseInt(ruleValue);
        } catch (NumberFormatException exception) {
            return;
        }

        int numberOfBids = auctionService.getNumberOfBiddersBid(auction.getId(), req.bidderId());

        if (chanceLimit <= numberOfBids) {
            throw new AlreadyUseBidChanceException("이미" + chanceLimit + " 번의 입찰 기회를 전부 사용하였습니다.");
        }
    }
}
