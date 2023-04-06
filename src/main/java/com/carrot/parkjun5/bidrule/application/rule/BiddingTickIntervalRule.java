package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.auction.application.AuctionService;
import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.auction.exception.NotEnoughBiddingPriceException;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@BidRuleName("TickIntervalRule")
@RequiredArgsConstructor
public class BiddingTickIntervalRule implements BiddingRule {
    
    private final AuctionService auctionService;

    @Override
    public void doValidate(BidRequest req, Auction auction, String ruleValue) {
        int tickInterval;
        try {
            tickInterval = Integer.parseInt(ruleValue);
        } catch (NumberFormatException exception) {
            return;
        }

        int nowBidPrice = auctionService.findLastBiddingPrice(auction);
        int minimumPrice = Auction.getMinimumPrice(nowBidPrice, BigDecimal.valueOf(tickInterval / 100));
        if (minimumPrice >= req.biddingPrice()) {
            throw new NotEnoughBiddingPriceException("최소금액 " + minimumPrice + "보다 제시하신 금액보다 낮습니다.");
        }
    }
}
