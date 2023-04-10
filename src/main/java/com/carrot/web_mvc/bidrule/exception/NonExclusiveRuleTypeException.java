package com.carrot.web_mvc.bidrule.exception;

import com.carrot.web_mvc.auction.exception.AuctionBusinessException;

public class NonExclusiveRuleTypeException extends AuctionBusinessException {
    public NonExclusiveRuleTypeException(String message) {
        super(message);
    }
}
