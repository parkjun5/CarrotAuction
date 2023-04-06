package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bid.application.dto.BidRequest;
import com.carrot.parkjun5.bidrule.application.BiddingRule;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@BidRuleName("TimeLimitRule")
@RequiredArgsConstructor
public class BiddingTimeLimitRule implements BiddingRule {
    
    @Override
    public void doValidate(BidRequest req, Auction auction, String ruleValue) {
        boolean isEndAuction = auction.getCloseDateTime().isBefore(ZonedDateTime.now());
        if (isEndAuction) {
            auction.endAuction();
        }
    }
}
