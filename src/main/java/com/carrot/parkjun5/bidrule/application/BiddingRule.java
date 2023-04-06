package com.carrot.parkjun5.bidrule.application;


import com.carrot.parkjun5.auction.domain.Auction;
import com.carrot.parkjun5.bid.application.dto.BidRequest;

public interface BiddingRule {
    void doValidate(BidRequest req, Auction auction, String ruleValue);
}
