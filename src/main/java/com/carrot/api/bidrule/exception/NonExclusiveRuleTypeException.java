package com.carrot.api.bidrule.exception;

import com.carrot.api.auction.exception.AuctionBusinessException;

public class NonExclusiveRuleTypeException extends AuctionBusinessException {
    public NonExclusiveRuleTypeException(String message) {
        super(message);
    }
}
