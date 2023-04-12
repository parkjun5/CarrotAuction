package com.carrot.parkjun5.bidrule.exception;

import com.carrot.parkjun5.auction.exception.AuctionBusinessException;

public class NonExclusiveRuleTypeException extends AuctionBusinessException {
    public NonExclusiveRuleTypeException(String message) {
        super(message);
    }
}
