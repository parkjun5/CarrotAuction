package com.carrot.core.bidrule.exception;

import com.carrot.core.auction.exception.AuctionBusinessException;

public class NonExclusiveRuleTypeException extends AuctionBusinessException {
    public NonExclusiveRuleTypeException(String message) {
        super(message);
    }
}
