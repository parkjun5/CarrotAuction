package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.auction.application.AuctionService;
import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@BidRuleName("TargetAmountRule")
@RequiredArgsConstructor
public class BiddingTargetAmountRule implements BiddingRule {
    private final AuctionService auctionService;

    @Override
    public void doValidate(BidRequest req, Auction auction, String ruleValue) {
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
