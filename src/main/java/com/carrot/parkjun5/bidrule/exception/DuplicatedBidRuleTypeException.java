package com.carrot.parkjun5.bidrule.exception;

import com.carrot.parkjun5.auction.exception.AuctionBusinessException;

public class DuplicatedBidRuleTypeException extends AuctionBusinessException {
    public DuplicatedBidRuleTypeException(String message) {
        super(message);
    }
}
