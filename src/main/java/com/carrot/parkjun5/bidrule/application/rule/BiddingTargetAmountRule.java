package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.auction.application.AuctionService;
import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@BidRuleName("TargetAmountRule")
@RequiredArgsConstructor
public class BiddingTargetAmountRule {
    private final AuctionService auctionService;

    public void validate(Auction auction, String ruleValue) {
        int targetAmount;
        try {
            targetAmount = Integer.parseInt(ruleValue);
        } catch (NumberFormatException exception) {
            return;
        }

        int nowBidPrice = auctionService.findLastBiddingPrice(auction);
        if (nowBidPrice >= targetAmount) {
            auction.endAuction();
        }
    }
}
