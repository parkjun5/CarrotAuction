package com.carrot.parkjun5.bidrule.application.rule;

import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bidrule.application.annotation.BidRuleName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@BidRuleName("TimeLimitRule")
@RequiredArgsConstructor
public class BiddingTimeLimitRule {

    public void validate(Auction auction) {
        boolean isEndAuction = auction.getCloseDateTime().isBefore(ZonedDateTime.now());
        if (isEndAuction) {
            auction.endAuction();
        }
    }
}
